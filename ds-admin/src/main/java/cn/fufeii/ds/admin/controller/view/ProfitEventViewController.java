package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分销事件 VIEW Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/profit-event")
public class ProfitEventViewController {

    @GetMapping("/")
    public String index() {
        return "/profitEvent/profitEvent.html";
    }

}