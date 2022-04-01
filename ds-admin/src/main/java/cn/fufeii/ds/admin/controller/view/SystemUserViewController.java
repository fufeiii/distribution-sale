package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户中心页 VIEW Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/system-user")
public class SystemUserViewController {

    @GetMapping("/")
    public String index() {
        return "/systemUser/systemUser.html";
    }

    @GetMapping("/add")
    public String add() {
        return "/systemUser/systemUserAdd.html";
    }

}