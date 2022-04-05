package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/")
    public String index() {
        return "/profitParam/profitParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage() {
        return "/profitParam/profitParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String editPage() {
        return "/profitParam/profitParamEdit.html";
    }

}