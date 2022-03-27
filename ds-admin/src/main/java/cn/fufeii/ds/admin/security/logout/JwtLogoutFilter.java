package cn.fufeii.ds.admin.security.logout;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * jwt登出过滤器
 *
 * @author FuFei
 * @date 2022/3/25
 */
public class JwtLogoutFilter extends LogoutFilter {

    public JwtLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
        super.setLogoutRequestMatcher(new AntPathRequestMatcher(DsAdminConstant.LOGOUT_URL, HttpMethod.POST.name()));
    }

}
