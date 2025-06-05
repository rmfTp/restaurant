package org.koreait.admin.trend.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.controllers.CommonController;
import org.koreait.trend.entities.Trend;
import org.koreait.trend.services.TrendInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trend")
public class TrendController extends CommonController {

    private final TrendInfoService infoService;

    @Override
    @ModelAttribute("mainCode")
    public String mainCode() {
        return "trend";
    }

    @GetMapping({"", "/news"})
    public String news(Model model){
        commonProcess("news",model);

        Trend item = infoService.getLastest("NEWS");
        model.addAttribute("item", item);

        return "admin/trend/news";
    }

    @GetMapping("/etc")
    public String etc(@ModelAttribute TrendSearch search, Model model){
        commonProcess("etc",model);

        return "admin/trend/etc";
    }

    /**
     * 공통처리
     * @param code : 서브메뉴 코드
     * @param model
     */
    private void commonProcess(String code, Model model){
        code = StringUtils.hasText(code)? code : "news";

        String pageTitle = "";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        addCommonScript.add("chart/chart");

        if (code.equals("news")){
            addScript.add("trend/news");
            pageTitle = "오늘의 뉴스 트렌드";
        }else if (code.equals("etc")){
            /**
             * 조별 코드
             */
        }

        model.addAttribute("subCode", code);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }
}
