package cn.fufeii.ds.admin.security.login;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.hutool.core.io.IoUtil;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt登录过滤器
 *
 * @author FuFei
 * @date 2022/3/25
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter() {
        super(new AntPathRequestMatcher(DsAdminConstant.LOGIN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        LoginRequest loginRequest = ObjectMapperUtil.toObject(IoUtil.read(request.getReader()), LoginRequest.class);
        PlatformUsernamePasswordAuthenticationToken authRequest = new PlatformUsernamePasswordAuthenticationToken(loginRequest.getPlatformUsername(), loginRequest.getUsername(), loginRequest.getPassword());
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Data
    private static class LoginRequest {
        private String platformUsername = "";
        private String username = "";
        private String password = "";
    }

}
