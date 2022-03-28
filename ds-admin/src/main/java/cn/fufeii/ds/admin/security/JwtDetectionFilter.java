package cn.fufeii.ds.admin.security;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.config.property.DsAdminProperties;
import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.fufeii.ds.repository.entity.SysUser;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.RegisteredPayload;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * jwt探测器
 * 若存在jwt则校验并放入，在{@link org.springframework.security.web.access.intercept.FilterSecurityInterceptor}之前
 * 把用户信息放入SecurityContext中
 *
 * @author FuFei
 * @date 2022/3/26
 */
@Slf4j
@Component
public class JwtDetectionFilter extends OncePerRequestFilter {
    private final byte[] signKeyByte;
    @Autowired
    private RedissonClient redissonClient;

    public JwtDetectionFilter(DsAdminProperties dsAdminProperties) {
        this.signKeyByte = dsAdminProperties.getJwtSignKey().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 只校验token存在的情况，不存在时由FilterSecurityInterceptor去校验匿名用户的合法性
        String jwtStr = request.getHeader(DsConstant.HEADER_AUTHORIZATION);
        if (jwtStr == null || jwtStr.trim().isEmpty()) {
            // 继续向下走
            filterChain.doFilter(request, response);
            return;
        }

        // 替换jwt规范种的Bearer，不遵守规范的情况也不影响。。
        jwtStr = jwtStr.replaceFirst(DsConstant.HEADER_AUTHORIZATION_PREFIX, CharSequenceUtil.EMPTY);
        JWT jwt;
        try {
            jwt = JWT.of(jwtStr).setSigner(JWTSignerUtil.hs256(signKeyByte));
        } catch (Exception exception) {
            log.warn("jwt解析错误，内容为[{}]，异常信息[{}]", jwtStr, exception.getMessage());
            throw new BadCredentialsException("jwt解析错误", exception);
        }

        if (!jwt.verify()) {
            throw new BadCredentialsException("jwt数据不完整");
        }

        if (!jwt.validate(0)) {
            throw new CredentialsExpiredException("jwt已失效");
        }

        String signature = StrUtil.split(jwtStr, CharUtil.DOT).get(2);
        boolean exists = redissonClient.getBucket(DsAdminConstant.REDIS_JWT_PREFIX + signature).isExists();
        if (exists) {
            throw new DisabledException("jwt已被注销");
        }

        // 验证成功
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        String subjectStr = jwt.getPayload(RegisteredPayload.SUBJECT).toString();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(ObjectMapperUtil.toObject(subjectStr, SysUser.class), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);

        // 继续向下走
        filterChain.doFilter(request, response);

    }

}
