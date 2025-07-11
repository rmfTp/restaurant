package org.koreait.admin.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.controllers.CommonController;
import org.koreait.admin.product.constants.ProductStatus;
import org.koreait.admin.product.services.ProductUpdateService;
import org.koreait.global.annotations.ApplyCommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductController extends CommonController {
    private final ProductUpdateService updateService;

    @Override
    @ModelAttribute("mainCode")
    public String mainCode(){
        return "product";
    }

    @ModelAttribute("addCss")
    public List<String> addCss() {
        return List.of("product/style");
    }

    @ModelAttribute("status")
    public ProductStatus[] statusList(){
        return ProductStatus.values();
    };

    @GetMapping({"","/list"})
    public String list(Model model){
        commonProcess("list",model);
        return "admin/product/list";
    }
    @GetMapping("/register")
    public String register(@ModelAttribute RequestProduct form, Model model){
        commonProcess("register",model);
        form.setGid(UUID.randomUUID().toString());
        form.setStatus(ProductStatus.READY);
        return "admin/product/register";
    }
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model){
        commonProcess("update",model);
        return "admin/product/update";
    }
    @PostMapping("/save")
    public String saveProduct(@Valid RequestProduct form, Model model, Errors errors){
        String mode = Objects.requireNonNullElse(form.getMode(),"add");
        commonProcess(mode.equals("edit")?"register":"update", model);
        if(errors.hasErrors()){
            return "admin/product/" + (mode.equals("edit") ? "update" : "register");
        }
        updateService.process(form);
        return "redirect:/admin/product";
    }

    /**
     * 공통 처리
     * @param code
     * @param model
     */
    private void commonProcess(String code, Model model){
        code = StringUtils.hasText(code)? code:"list";
        String pageTitle = "";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(List.of("register","update").contains(code)){
            addCommonScript.add("fileManager");
            addScript.add("product/form");
            pageTitle=code.equals("update")?"상품정보 수정":"상품등록";
        }else if (code.equals("list")){
            pageTitle = "상품목록";
        }

        model.addAttribute("pageTitle",pageTitle);
        model.addAttribute("subCode",code);
        model.addAttribute("addCommonScript",addCommonScript);
        model.addAttribute("addScript",addScript);
    }
}
