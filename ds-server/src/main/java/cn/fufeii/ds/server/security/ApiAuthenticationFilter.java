package cn.fufeii.ds.server.security;

import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.fufeii.ds.common.util.ResponseUtil;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.config.property.DsServerProperties;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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
    private final AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    private DsServerProperties dsServerProperties;
    @Autowired
    private CrudPlatformService crudPlatformService;

    public static void main(String[] args) {
        String waitSignStr = "POST\n" + "/api/member/create\n" + "{\"avatar\": \"\",\"inviteUsername\": \"\",\"nickname\": \"123\",\"username\": \"31\"}\n";
        String data = SecureUtil.hmacSha256("eta3m6xh65v5wlsqxc69qlq6a4aeyjna").digestHex(waitSignStr);
        System.out.println("data = " + data);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // doc文档不需要过滤
        return Arrays.stream(DsConstant.KNIFE4J_URL).anyMatch(it -> matcher.match(it, request.getServletPath()));
    }

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

        // 验签操作
        HttpServletRequest requestWrapper = request;
        try {
            // 验签，platform.sk
            if (dsServerProperties.getEnableApiSignature()) {
                ContentReuseRequestWrapper reuseRequestWrapper = new ContentReuseRequestWrapper(request);
                // 改变引用，以便进一步处理请求体
                requestWrapper = reuseRequestWrapper;
                String requestBody = StrUtil.utf8Str(reuseRequestWrapper.getContent());
                // 执行验签逻辑
                String waitSignStr = requestWrapper.getMethod() + "\n" + requestWrapper.getServletPath() + "\n" + requestBody + "\n";
                boolean checkSignature = this.checkSignature(waitSignStr, authorizationParam.getSignature(), platform.getSk());
                if (!checkSignature) {
                    ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "验签错误"));
                    return;
                }
            }
        } catch (RuntimeException e) {
            log.warn("执行错误：", e);
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.SERVER_API_AUTH_ERROR, "未知错误"));
            return;
        }

        // 验证通过
        CurrentPlatformHolder.set(platform);
        try {
            filterChain.doFilter(requestWrapper, response);
        } finally {
            CurrentPlatformHolder.remove();
        }

    }

    /**
     * 验签
     * hmacSha256
     */
    private boolean checkSignature(String waitSignStr, String signature, String sk) {
        // 获取请求体并比较签名
        String digestBase64 = SecureUtil.hmacSha256(sk).digestHex(waitSignStr);
        if (!Objects.equals(digestBase64, signature)) {
            log.debug("验签失败：待签名字符串[{}]， 签名[{}]，原签名[{}]", waitSignStr, digestBase64, signature);
            return false;
        }
        return true;
    }


}
