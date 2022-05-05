package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 段位配置 VIEW Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/member-rank-config")
public class MemberRankConfigViewController {

    @GetMapping("/")
    public String index() {
        return "/memberRankConfig/memberRankConfig.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String add() {
        return "/memberRankConfig/memberRankConfigAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String edit() {
        return "/memberRankConfig/memberRankConfigEdit.html";
    }

}