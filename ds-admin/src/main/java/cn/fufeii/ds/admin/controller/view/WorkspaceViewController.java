package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 工作台页 Controller
 *
 * @author FuFei
 * @date 2021/8/29
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/workspace")
public class WorkspaceViewController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "/workspace/dashboard.html";
    }

    @GetMapping("/statistics")
    public String statistics() {
        return "/workspace/dashboard.html";
    }

}
