package cn.fufeii.ds.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * security配置
 *
 * @author FuFei
 * @date 2021/8/28
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(bCryptPasswordEncoder().encode("admin"))
                .authorities("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源不经过security
        web.ignoring().mvcMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录相关请求不做限制
        http
                .authorizeRequests()
                .mvcMatchers("/toLogin", "/doLogin")
                .permitAll();

        // FIXME 暂时为表单登录，后续改为基于JWT的认证方式
        http
                .formLogin()
                .loginPage("/toLogin")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/index");


        // 所有请求都需要登录
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        // 关闭一些无用配置
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable();
    }

}
