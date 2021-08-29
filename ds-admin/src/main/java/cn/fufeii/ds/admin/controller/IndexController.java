package cn.fufeii.ds.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器
 *
 * @author FuFei
 * @date 2021/8/28
 */
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "/index.html";
    }

}
