package cn.fufeii.ds.admin.security.login;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * jwt-filter-provider 认证成功/失败逻辑
 *
 * @author FuFei
 * @date 2022/3/25
 */
public class JwtLoginFilterHandler {

    @Slf4j
    public static class FailureHandler implements AuthenticationFailureHandler {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
            // 响应登录错误信息
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(objectMapper.writeValueAsString(CommonResult.fail(ExceptionEnum.LOGIN_ERROR, exception.getMessage())));
        }
    }


    @Slf4j
    public static class SuccessHandler implements AuthenticationSuccessHandler {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private final byte[] signKeyByte;
        private final long ttl;

        public SuccessHandler(String signKey, long ttl) {
            this.signKeyByte = signKey.getBytes(StandardCharsets.UTF_8);
            this.ttl = ttl;
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            Date now = new Date();
            // 签发jwt
            String jwtToken = JWT.create()
                    .setSubject(principal)
                    .setNotBefore(now)
                    .setIssuedAt(now)
                    .setExpiresAt(new Date(now.getTime() + ttl))
                    .setSigner(JWTSignerUtil.hs256(signKeyByte))
                    .sign();
            // 响应
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(jwtToken)));
        }

    }

}
