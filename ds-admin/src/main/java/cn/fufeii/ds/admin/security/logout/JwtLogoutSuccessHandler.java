package cn.fufeii.ds.admin.security.logout;

import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.util.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 不是先任何逻辑
 *
 * @author FuFei
 * @date 2022/3/27
 */
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtil.write(response, CommonResult.success());
    }

}