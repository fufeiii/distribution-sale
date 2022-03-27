package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登录页 VIEW Controller
 *
 * @author FuFei
 * @date 2021/8/28
 */
@Controller
public class LoginPageViewController {


    @GetMapping(value = DsAdminConstant.VIEW_PATH_PREFIX + "/login")
    public String login() {
        return "/login.html";
    }

}
