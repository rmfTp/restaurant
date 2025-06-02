package org.koreait.global.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.koreait.member.libs.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {
    private final MemberUtil memberUtil;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /**
         * 로그인회원정보 유지
         */
        if (modelAndView != null){
            modelAndView.addObject("isLogin",memberUtil.isLogin());
            modelAndView.addObject("isAdmin",memberUtil.isAdmin());
            modelAndView.addObject("loggedMember",memberUtil.getMember());
        }
    }
}
