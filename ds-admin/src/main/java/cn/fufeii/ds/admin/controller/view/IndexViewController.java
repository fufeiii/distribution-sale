package cn.fufeii.ds.admin.controller.view;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页 VIEW Controller
 *
 * @author FuFei
 * @date 2021/8/28
 */
@Controller
public class IndexViewController {

    private static final String INDEX_PATH = DsAdminConstant.VIEW_PATH_PREFIX + "/index";

    @GetMapping(INDEX_PATH)
    public String index() {
        return "/index.html";
    }

    @GetMapping(DsAdminConstant.ROOT_PATH)
    public String root() {
        return "redirect:" + INDEX_PATH;
    }

}
