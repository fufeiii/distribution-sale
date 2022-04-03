package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 段位参数 VIEW Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping(DsAdminConstant.VIEW_PATH_PREFIX + "/rank-param")
public class RankParamViewController {

    /**
     * 封装固定的参数
     */
    private void injectionParam(Model model) {
        // 遍历 会员段位 枚举
        model.addAttribute("mre", MemberRankTypeEnum.getKeyValuePairList());
        // 遍历 状态 枚举
        model.addAttribute("se", StateEnum.getKeyValuePairList());
    }


    @GetMapping("/")
    public String index(Model model) {
        this.injectionParam(model);
        return "/rankParam/rankParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage(Model model) {
        return "/rankParam/rankParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String edit(Model model) {
        return "/rankParam/rankParamEdit.html";
    }

}