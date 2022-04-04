package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/")
    public String index() {
        return "/rankParam/rankParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage() {
        return "/rankParam/rankParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String edit() {
        return "/rankParam/rankParamEdit.html";
    }

}