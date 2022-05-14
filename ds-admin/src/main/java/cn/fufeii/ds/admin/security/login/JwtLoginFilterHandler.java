package cn.fufeii.ds.admin.security.login;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.fufeii.ds.common.util.ResponseUtil;
import cn.fufeii.ds.repository.entity.SystemUser;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
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

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
            // 响应登录错误信息
            ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.LOGIN_ERROR, exception.getMessage()));
        }
    }


    @Slf4j
    public static class SuccessHandler implements AuthenticationSuccessHandler {
        private final byte[] signKeyByte;
        private final long ttl;

        public SuccessHandler(String signKey, long ttl) {
            this.signKeyByte = signKey.getBytes(StandardCharsets.UTF_8);
            this.ttl = ttl;
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            SystemUser systemUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 脱敏后存入jwt
            SystemUser subject = new SystemUser();
            subject.setId(systemUser.getId());
            subject.setPlatformUsername(systemUser.getPlatformUsername());
            subject.setPlatformNickname(systemUser.getPlatformNickname());
            subject.setUsername(systemUser.getUsername());
            subject.setNickname(systemUser.getNickname());
            subject.setAvatar(systemUser.getAvatar());
            subject.setState(systemUser.getState());

            Date now = new Date();
            // 签发jwt
            String jwtStr = JWT.create()
                    .setSubject(ObjectMapperUtil.toJsonString(subject))
                    .setNotBefore(now)
                    .setIssuedAt(now)
                    .setExpiresAt(new Date(now.getTime() + ttl))
                    .setSigner(JWTSignerUtil.hs256(signKeyByte))
                    .sign();
            // 响应
            ResponseUtil.write(response, CommonResult.success(jwtStr));
        }

    }

}
