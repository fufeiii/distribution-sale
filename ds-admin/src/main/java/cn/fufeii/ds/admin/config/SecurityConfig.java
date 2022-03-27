package cn.fufeii.ds.admin.config;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.config.property.DsAdminProperties;
import cn.fufeii.ds.admin.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * security配置
 *
 * @author FuFei
 * @date 2022/3/25
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private DsAdminProperties dsAdminProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源不经过security，因为不需要上下文中的用户信息
        web.ignoring().mvcMatchers("/ds/**", "/component/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        String jwtSignKey = dsAdminProperties.getJwtSignKey();

        // 全局接口权限设置
        httpSecurity
                .authorizeRequests()
                // 登录请求和跳转登录页可以匿名访问（默认配置是打开了匿名用户filter的）
                .mvcMatchers(this.getAnonymousRequestPattern())
                .anonymous()
                // 其他请求都需要经过认证
                .anyRequest().authenticated();

        // 添加jwt验证过滤器到UsernamePasswordAuthenticationFilter后面
        httpSecurity.addFilterAfter(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加jwt检测过滤器到ExceptionTranslationFilter后面，这样抛出异常可以被JwtAuthenticationEntryPoint处理
        httpSecurity.addFilterAfter(new JwtDetectionFilter(jwtSignKey), ExceptionTranslationFilter.class);

        // 添加Provider到AuthenticationProvider列表中
        httpSecurity.authenticationProvider(jwtAuthenticationProvider);

        // 若直到FilterSecurityInterceptor（最后一个filter）没有认证成功或其权限不足，
        // 则会抛出AuthenticationException或者AccessDeniedException，这时交由JwtAuthenticationEntryPoint处理
        httpSecurity.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        // 关闭session，即不返回cookie（若使用了依赖session的功能(如csrf)则还是会生成cookie到客户端）
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 关闭用不上的默认配置
        httpSecurity
                .csrf().disable()
                .securityContext().disable()
                .requestCache().disable()
                .servletApi().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable();

    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter phoneAuthenticationFilter = new JwtAuthenticationFilter();
        // 配置filter验证成功逻辑
        phoneAuthenticationFilter.setAuthenticationSuccessHandler(
                new JwtAuthenticationHandler.SuccessHandler(dsAdminProperties.getJwtSignKey(), dsAdminProperties.getJwtSignTtl().toMillis()));
        // 配置filter验证失败逻辑
        phoneAuthenticationFilter.setAuthenticationFailureHandler(new JwtAuthenticationHandler.FailureHandler());
        // 为此filter设置am管理（am才真正持有AuthenticationProvider集合）
        phoneAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        return phoneAuthenticationFilter;
    }

    private String[] getAnonymousRequestPattern() {
        List<String> anonymousRequestPath = new ArrayList<>();
        // 登录请求
        anonymousRequestPath.add(DsAdminConstant.LOGIN_URL);
        // 前端页面
        anonymousRequestPath.add(DsAdminConstant.ROOT_PATH);
        anonymousRequestPath.add(DsAdminConstant.VIEW_PATH_PREFIX + "/**");
        return anonymousRequestPath.toArray(new String[3]);
    }

}
