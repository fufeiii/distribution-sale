package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/common/")
public class CommonController {

    @GetMapping("/space")
    public String index() {
        return "/common/space.html";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "/common/welcome.html";
    }

}
