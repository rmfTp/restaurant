package org.koreait.survey.diabetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.global.constants.Gender;
import org.koreait.global.libs.Utils;
import org.koreait.survey.diabetes.constants.SmokingHistory;
import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.koreait.survey.diabetes.services.DiabetesSurveyInfoService;
import org.koreait.survey.diabetes.services.DiabetesSurveyService;
import org.koreait.survey.diabetes.validators.DiabetesSurveyValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/survey/diabetes")
@SessionAttributes("requestDiabetesSurvey")
public class DiabetesSurveyController {

    private final Utils utils;
    private final DiabetesSurveyValidator validator;
    private final DiabetesSurveyService surveyService;
    private final DiabetesSurveyInfoService infoService;

    @ModelAttribute("addCss")
    public List<String> addCss(){
        return List.of("survey/diabetes/style");
    }

    @ModelAttribute("requestDiabetesSurvey")
    public RequestDiabetesSurvey requestDiabetesSurvey() {
        RequestDiabetesSurvey form = new RequestDiabetesSurvey();
        form.setGender(Gender.FEMALE);
        form.setSmokingHistory(SmokingHistory.CURRENT);
        return new RequestDiabetesSurvey();
    }

    @ModelAttribute("genders")
    public Gender[] genders(){
        return Gender.values();
    }

    @ModelAttribute("smokingHistories")
    public SmokingHistory[] smokingHistories(){
        return SmokingHistory.values();
    }

    @GetMapping("/step1")
    public String step1(@ModelAttribute RequestDiabetesSurvey form, Model model) {
        commonProcess("step", model);
        model.addAttribute("requestDiabetesSurvey", form);

        return utils.tpl("survey/diabetes/step1");
    }

    @PostMapping("/step2")
    public String step2(@Valid RequestDiabetesSurvey form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            commonProcess("step", model);
            return utils.tpl("survey/diabetes/step1");
        }

        return utils.tpl("survey/diabetes/step2");
    }
    @PostMapping("/result")
    public String result(@Valid RequestDiabetesSurvey form, Errors errors, Model model) {

        return utils.tpl("survey/diabetes/result");
    }


    /**
     * 설문 저장 및 결과 처리
     *
     * @param form
     * @param errors
     * @return
     */
    @PostMapping("/process")
    public String process(@Valid RequestDiabetesSurvey form, Errors errors, Model model, SessionStatus status) {
        commonProcess("step", model);

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("survey/diabetes/step2");
        }

        surveyService.process(form);

        status.setComplete();

        return "redirect:/survey/diabetes/result/설문번호";
    }

    @GetMapping("/result/{seq}")
    public String result(@PathVariable("seq") Long seq, Model model){
        commonProcess("result", model);

        DiabetesSurvey item = infoService.get(seq);
        model.addAttribute("item",item);

        return utils.tpl("survey/diabetes/result");
    }


    /**
     * 컨트롤러 공통처리
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model){
        mode = StringUtils.hasText(mode) ? mode : "step";

        String pageTitle = "";

        if (mode.equals("step")){
            pageTitle = utils.getMessage("당뇨_고위험군_테스트");
        } else if (mode.equals("result")) {
            pageTitle = utils.getMessage("당뇨_고위험군_테스트_결과");
        }
        model.addAttribute("pageTitle", pageTitle);
    }
}