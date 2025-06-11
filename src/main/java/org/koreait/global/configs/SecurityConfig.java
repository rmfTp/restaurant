package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.member.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberInfoService memberInfoService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(c->{
           c.loginPage("/member/login").usernameParameter("email").passwordParameter("password")
                   .successHandler(new LoginSuccessHandler())
                   .failureHandler(new LoginFailureHandler());
        });

        http.logout(c -> {
           c.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/member/login");
        });

        http.rememberMe(c->{
            c.rememberMeParameter("autoLogin")
                    .tokenValiditySeconds(60*60*24*7)
                    .userDetailsService(memberInfoService)
                    .authenticationSuccessHandler(new LoginSuccessHandler());
        });

        /**
         * authenticated : 회원
         * anonymous : 비회원
         * permitAll : 전원
         * hasAuthority : 특정 권한 필요
         * hasAnyAuthority : 다수의 권한 중 하나 이상 필요
         * hasRole : 특정 권한(role) 필요
         *      권한 선언시 ROLE_권한이름
         * hasAnyRole : 다수의 권한(role) 중 하나 이상 필요
         */
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated()
                    .requestMatchers("/mypage/join","/member/login").anonymous()
                  //.requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().permitAll();
        });

        http.exceptionHandling(c -> {
            c.authenticationEntryPoint(new MemberAuthenticationExceptionHandler());
            c.accessDeniedHandler(new MemberAccessDeniedHandler());
        });

        http.headers(c -> c.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
