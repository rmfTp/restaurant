package org.koreait.admin.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller("adminMainController")
public class MainController {

    @GetMapping
    public String index() {
        return "admin/main/index";
    }
}
