package cn.fufeii.ds.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author FuFei
 * @date 2021/8/28
 */
@Controller
public class LoginController {

    @GetMapping("/toLogin")
    public String login() {
        return "/login.html";
    }

}
