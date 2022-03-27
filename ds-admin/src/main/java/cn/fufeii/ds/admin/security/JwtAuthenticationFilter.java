package cn.fufeii.ds.admin.security;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt过滤器
 *
 * @author FuFei
 * @date 2022/3/25
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher(DsAdminConstant.LOGIN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        LoginRequest loginRequest = objectMapper.readValue(IoUtil.readBytes(request.getInputStream()), LoginRequest.class);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Data
    private static class LoginRequest {
        private String username = "";
        private String password = "";
    }

}
