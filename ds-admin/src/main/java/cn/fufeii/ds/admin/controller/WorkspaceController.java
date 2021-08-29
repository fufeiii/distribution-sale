package cn.fufeii.ds.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 工作台控制器
 *
 * @author FuFei
 * @date 2021/8/29
 */
@Controller
@RequestMapping("/workspace")
public class WorkspaceController {

    private final String baseDir = "/workspace/";

    @GetMapping("/dashboard")
    public String dashboard() {
        return baseDir + "dashboard.html";
    }

}
