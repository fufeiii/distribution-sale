package cn.fufeii.ds.portal.security;

import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.fufeii.ds.common.util.ResponseUtil;
import cn.fufeii.ds.portal.config.property.DsPortalProperties;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.entity.Platform;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * api认证filter
 * 从header中获取平台信息
 * 按照配置进行解密和验签
 *
 * @author FuFei
 * @date 2022/4/5
 */
@Slf4j
@Component
public class ApiAuthenticationFilter extends OncePerRequestFilter {
    private final ThreadLocal<Platform> platformThreadLocal = new InheritableThreadLocal<>();
    @Autowired
    private DsPortalProperties dsPortalProperties;
    @Autowired
    private CrudPlatformService crudPlatformService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationStr = request.getHeader(DsConstant.HEADER_AUTHORIZATION);
        // 没传有效的签名
        if (CharSequenceUtil.isBlank(authorizationStr)) {
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, DsConstant.HEADER_AUTHORIZATION + "为空"));
            return;
        }

        // 准备参数
        authorizationStr = authorizationStr.trim();
        Map<String, String> authMap = Arrays.stream(authorizationStr.split(StrPool.COMMA)).collect(Collectors.toMap(
                itKey -> itKey.split(StringPool.EQUALS)[0]
                , itValue -> itValue.substring(itValue.indexOf(StringPool.EQUALS) + 1)
                , (o, n) -> n));
        // 需要更优雅的方式转换
        AuthorizationParam authorizationParam = ObjectMapperUtil.toObject(ObjectMapperUtil.toJsonString(authMap), AuthorizationParam.class);
        Long pid = authorizationParam.getPid();
        if (pid == null) {
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "pid为空"));
            return;
        }
        String signature = authorizationParam.getSignature();
        if (StrUtil.isBlank(signature)) {
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "signature为空"));
            return;
        }

        // 获取平台信息
        Optional<Platform> platformOptional = crudPlatformService.selectByIdOptional(pid);
        if (!platformOptional.isPresent()) {
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "平台未创建"));
            return;
        }
        Platform platform = platformOptional.get();
        if (StateEnum.DISABLE == platform.getState()) {
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "平台被禁用"));
            return;
        }

        // 加密和签名机制
        Boolean enableApiEncryption = dsPortalProperties.getEnableApiEncryption();
        Boolean enableApiSignature = dsPortalProperties.getEnableApiSignature();
        HttpServletRequest requestWrapper = request;
        String requestBody = "";
        if (enableApiEncryption || enableApiSignature) {
            ContentReuseRequestWrapper reuseRequestWrapper = new ContentReuseRequestWrapper(request);
            requestWrapper = reuseRequestWrapper;
            requestBody = new String(reuseRequestWrapper.getContent());
        }

        // 解密和验签操作
        try {
            // 解密，platform.username作为ak使用
            if (enableApiEncryption) {
                requestBody = this.decodeBody(requestBody, platform.getUsername());
            }
            // 验签，platform.sk作为ak使用
            if (enableApiSignature) {
                String waitSignStr = requestWrapper.getMethod() + "\n" + requestWrapper.getServletPath() + "\n" + requestBody + "\n";
                boolean checkSignature = this.checkSignature(waitSignStr, authorizationParam.getSignature(), platform.getSk());
                if (!checkSignature) {
                    ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "验签错误"));
                }
            }
        } catch (RuntimeException e) {
            log.warn("执行错误：", e);
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "未知错误"));
            return;
        }

        // 验证通过
        platformThreadLocal.set(platform);
        try {
            filterChain.doFilter(requestWrapper, response);
        } finally {
            platformThreadLocal.remove();
        }

    }


    /**
     * 解密
     * aes256
     */
    private String decodeBody(String requestBody, String ak) {
        int keyLength = 32;
        int keyMissingLength = keyLength - ak.length();
        String key;
        if (keyMissingLength > 0) {
            key = ak + StrUtil.repeat('0', keyMissingLength);
        } else {
            // 理论上这个不会走这个分支，因为数据库限制了ds_platform.username最大32
            key = ak.substring(0, keyLength);
        }
        return new AES(StrUtil.utf8Bytes(key)).decryptStr(requestBody);
    }


    /**
     * 验签
     * hmacSha256
     */
    private boolean checkSignature(String waitSignStr, String signature, String sk) {
        // 获取请求体并比较签名
        String digestBase64 = SecureUtil.hmacSha256(sk).digestBase64(waitSignStr, false);
        if (!Objects.equals(digestBase64, signature)) {
            log.debug("验签失败：待签名字符串[{}]， 签名[{}]，原签名[{}]", waitSignStr, digestBase64, signature);
            return false;
        }
        return true;
    }


}
