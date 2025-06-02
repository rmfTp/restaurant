package org.koreait.member.libs;

import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class MemberUtil {
    public boolean isLogin(){
        return getMember() != null;
    }

    public boolean isAdmin(){
        return isLogin() && getMember().getAuthority() == Authority.ADMIN;
    }

    public Member getMember(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null&&auth.getPrincipal() instanceof MemberInfo memberInfo)
            return memberInfo.getMember();
        return null;
    }
}
