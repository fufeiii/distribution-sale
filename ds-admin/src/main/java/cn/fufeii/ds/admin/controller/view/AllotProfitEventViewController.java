package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分润事件 VIEW Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/allot-profit-event")
public class AllotProfitEventViewController {

    @GetMapping("/")
    public String index() {
        return "/allotProfitEvent/allotProfitEvent.html";
    }

    @GetMapping("/info")
    public String info() {
        return "/allotProfitEvent/allotProfitEventInfo.html";
    }

}