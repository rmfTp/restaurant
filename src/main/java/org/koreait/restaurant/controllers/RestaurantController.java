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
        List<String> category = new ArrayList<>();
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = utils.getMessage("오늘의_식당");
            addCss.add("restaurant/list");
            addScript.add("map");
            addScript.add("restaurant/list");
        }
        List<String> categories = List.of("한식", "경양식", "까페", "분식", "횟집", "호프/통닭", "기타", "중국식", "일식", "식육(숯불구이)", "뷔페식", "김밥(도시락)", "정종/대포집/소주방", "외국음식전문점(인도,태국등)", "냉면집", "탕류(보신용)", "패밀리레스트랑", "감성주점", "출장조리", "라이브카페", "키즈카페", "통닭(치킨)", "복어취급", "패스트푸드", "전통찻집", "이동조리", "기타 휴게음식점", "커피숍");
        model.addAttribute("category", categories);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
    }
}
