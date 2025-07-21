package org.koreait.global.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        clearSocialToken(request);
        return true;
    }

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
    // socialType 과 socialToken은 멤버/조인 에서만 유지 나머지 제거
    private void clearSocialToken(HttpServletRequest request){
        String url = request.getRequestURI();
        if (!url.contains("/member/join")) {
            HttpSession session = request.getSession();
            session.removeAttribute("socialType");
            session.removeAttribute("socialToken");
        }
    }
}
