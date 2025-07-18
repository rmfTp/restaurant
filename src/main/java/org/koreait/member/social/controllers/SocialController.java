package org.koreait.member.social.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.member.social.constants.SocialType;
import org.koreait.member.social.services.KakaoLoginService;
import org.koreait.member.social.services.NaverLoginService;
import org.koreait.member.social.services.SocialLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("member/social")
public class SocialController {
    private final HttpSession session;
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;
    @GetMapping("/callback/{channel}")
    public String callback(@PathVariable("channel") String type, @RequestParam("code") String code, @RequestParam(name = "state", required = false) String redirectUrl) {
        SocialType socialType = SocialType.valueOf(type.toUpperCase());
        SocialLoginService service = socialType == SocialType.NAVER ? naverLoginService : kakaoLoginService;

        String token = service.getToken(code);

        if (service.login(token))return "redirect:" + (StringUtils.hasText(redirectUrl) ? redirectUrl : "/");

        session.setAttribute("socialType", socialType);
        session.setAttribute("socialToken", token);

        return "redirect:/member/join";
    }
}
