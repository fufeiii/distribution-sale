package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分销事件 VIEW Controller
 *
 * @author FuFei
 */
@Api("提现申请")
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/withdraw-apply")
public class WithdrawApplyViewController {

    @GetMapping("/")
    public String index() {
        return "/withdrawApply/withdrawApply.html";
    }

}