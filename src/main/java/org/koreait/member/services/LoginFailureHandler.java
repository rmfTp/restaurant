package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.member.controllers.RequestLogin;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    /**
     * AuthenticationException exception : 인증 실패 시 발생하는 예외
     * 상황에 따라 다른 예외 발생
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("exception:" + exception);
        HttpSession session = request.getSession();
        RequestLogin form = (RequestLogin)session.getAttribute("requestLogin");
        form = Objects.requireNonNullElseGet(form, RequestLogin::new);

        List<String> fieldErrors = new ArrayList<>();
        List<String> globalErrors = new ArrayList<>();

        String email = request.getParameter("email");
        form.setEmail(email);
        String password = request.getParameter("password");
        form.setPassword(password);
        form.setFieldErrors(fieldErrors);
        form.setGlobalErrors(globalErrors);
        /**
         * 이메일 || 비밀번호 누락
         * 이메일 || 비밀번호 불일치
         */
        if (exception instanceof BadCredentialsException){
            if (!StringUtils.hasText(email))fieldErrors.add("email_NotBlank");
            if (!StringUtils.hasText(password))fieldErrors.add("password_NotBlank");
            if (fieldErrors.isEmpty())globalErrors.add("Authentication.bad.credential");

        }

        if (exception instanceof DisabledException) { // 탈퇴한 회원인 경우Add commentMore actions
            globalErrors.add("Authentication.disabled");
        } else if (exception instanceof AccountExpiredException) {
            globalErrors.add("Authentication.account.exfired");
        } else if (exception instanceof LockedException) {
            globalErrors.add("Authentication.account.Locked");
        } else if (exception instanceof CredentialsExpiredException) {
            response.sendRedirect(request.getContextPath()+"/member/password");
            return;
        }
        session.setAttribute("requestLogin", form);

        // 로그인 실패시 로그인 페이지로 이동
        response.sendRedirect(request.getContextPath()+"/member/login");
    }
}
