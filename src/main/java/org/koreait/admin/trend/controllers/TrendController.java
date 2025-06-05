package org.koreait.admin.trend.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.controllers.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trend")
public class TrendController extends CommonController {

    @Override
    @ModelAttribute("mainCode")
    public String mainCode() {
        return "trend";
    }

    @GetMapping({"", "/news"})
    public String news(Model model){
        commonProcess("news",model);
        return "admin/trend/news";
    }

    @GetMapping("/etc")
    public String etc(Model model){
        commonProcess("etc",model);
        return "admin/trend/etc";
    }

    /**
     * 공통처리
     * @param code : 서브메뉴 코드
     * @param model
     */
    private void commonProcess(String code, Model model){
        List<String> addCommonScript = new ArrayList<>();

        addCommonScript.add("chart/chart");

        model.addAttribute("subCode", code);
        model.addAttribute("addCommonScript", addCommonScript);
    }
}
