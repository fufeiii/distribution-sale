package cn.fufeii.ds.admin.config;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.config.property.DsAdminProperties;
import cn.fufeii.ds.admin.security.JwtAuthenticationEntryPoint;
import cn.fufeii.ds.admin.security.JwtDetectionFilter;
import cn.fufeii.ds.admin.security.login.JwtLoginFilter;
import cn.fufeii.ds.admin.security.login.JwtLoginFilterHandler;
import cn.fufeii.ds.admin.security.login.JwtLoginProvider;
import cn.fufeii.ds.admin.security.logout.JwtLogoutFilter;
import cn.fufeii.ds.admin.security.logout.JwtLogoutHandler;
import cn.fufeii.ds.admin.security.logout.JwtLogoutSuccessHandler;
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
    private JwtLoginProvider jwtLoginProvider;
    @Autowired
    private JwtLogoutHandler jwtLogoutHandler;
    @Autowired
    private JwtDetectionFilter jwtDetectionFilter;
    @Autowired
    private DsAdminProperties dsAdminProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源不经过security
        web.ignoring()
                .mvcMatchers("/ds/**", "/component/**", "/favicon.ico")
                .mvcMatchers("/doc.html", "/webjars/**")
                .mvcMatchers("/swagger-resources", "/v3/api-docs")
        ;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 全局接口权限设置
        httpSecurity.authorizeRequests()
                // 登录请求和跳转登录页可以匿名访问（默认配置是打开了匿名用户filter的）
                .mvcMatchers(this.getPermitAllRequestPattern()).permitAll()
                // 其他请求都需要经过认证
                .anyRequest().authenticated();

        // 添加jwt登录过滤器
        httpSecurity.addFilterAfter(this.getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加jwt检测过滤器
        httpSecurity.addFilterAfter(jwtDetectionFilter, ExceptionTranslationFilter.class);
        // 添加jwt登出过滤器
        httpSecurity.addFilterAfter(new JwtLogoutFilter(new JwtLogoutSuccessHandler(), jwtLogoutHandler), JwtDetectionFilter.class);

        // 添加Provider到AuthenticationProvider列表中
        httpSecurity.authenticationProvider(jwtLoginProvider);

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

    private JwtLoginFilter getJwtAuthenticationFilter() throws Exception {
        JwtLoginFilter phoneAuthenticationFilter = new JwtLoginFilter();
        // 配置filter验证成功逻辑
        phoneAuthenticationFilter.setAuthenticationSuccessHandler(
                new JwtLoginFilterHandler.SuccessHandler(dsAdminProperties.getJwtSignKey(), dsAdminProperties.getJwtSignTtl().toMillis()));
        // 配置filter验证失败逻辑
        phoneAuthenticationFilter.setAuthenticationFailureHandler(new JwtLoginFilterHandler.FailureHandler());
        // 为此filter设置am管理（am才真正持有AuthenticationProvider集合）
        phoneAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        return phoneAuthenticationFilter;
    }

    private String[] getPermitAllRequestPattern() {
        List<String> anonymousRequestPath = new ArrayList<>();
        // 登录/登出
        anonymousRequestPath.add(DsAdminConstant.LOGIN_URL);
        // 前端页面
        anonymousRequestPath.add(DsAdminConstant.ROOT_PATH);
        anonymousRequestPath.add(DsAdminConstant.VIEW_PATH_PREFIX + "/**");
        return anonymousRequestPath.toArray(new String[0]);
    }

}
