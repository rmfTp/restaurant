package org.koreait.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.global.libs.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@ApplyCommonController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final Utils utils;

    @GetMapping({"", "/list"})
    public String list(@ModelAttribute RestaurantSearch search, Model model) {
        commonProcess("list", model);
        return utils.tpl("restaurant/list");
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";

        String pageTitle = "";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = utils.getMessage("오늘의_식당");
            addCss.add("restaurant/list");
            addScript.add("restaurant/list");
            addScript.add("map");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript); // ✅ 오타 수정
    }
}
