package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.enumerate.biz.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分润参数 VIEW Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/profit-param")
public class ProfitParamViewController {

    /**
     * 封装固定的参数
     */
    private void injectionParam(Model model) {
        // 遍历 账户类型 枚举
        model.addAttribute("ate", AccountTypeEnum.getKeyValuePairList());
        // 遍历 分润类型 枚举
        model.addAttribute("pte", ProfitTypeEnum.getKeyValuePairList());
        // 遍历 分润计算方式 枚举
        model.addAttribute("cme", CalculateModeEnum.getKeyValuePairList());
        // 分销等级 暂时先这样模拟一下
        model.addAttribute("ple", ProfitLevelEnum.getKeyValuePairList());
        // 遍历 会员段位 枚举
        model.addAttribute("mre", MemberRankTypeEnum.getKeyValuePairList());
        // 遍历 会员身份 枚举
        model.addAttribute("mte", MemberIdentityTypeEnum.getKeyValuePairList());
        // 遍历 状态 枚举
        model.addAttribute("se", StateEnum.getKeyValuePairList());
    }


    @GetMapping("/")
    public String index(Model model) {
        this.injectionParam(model);
        return "/profitParam/profitParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage(Model model) {
        this.injectionParam(model);
        return "/profitParam/profitParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String editPage(Model model) {
        this.injectionParam(model);
        return "/profitParam/profitParamEdit.html";
    }

}