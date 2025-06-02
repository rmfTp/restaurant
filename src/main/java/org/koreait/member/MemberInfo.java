package org.koreait.member;

import lombok.Builder;
import lombok.Data;
import org.koreait.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Member member;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 권한 설정 - 인가 통제
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 계정이 만료 되지 않았는지
    @Override
    public boolean isAccountNonExpired() {
        LocalDateTime date = member.getExpired();
        LocalDateTime now = LocalDateTime.now();
        return date == null||date.isAfter(now);
    }

    // 회원 탈퇴 여부
    @Override
    public boolean isEnabled() {
        return member.getDeletedAt() == null;
    }

    // 비밀번호가 만료 되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        LocalDateTime date = member.getCredentialChangedAt();
        LocalDateTime now = LocalDateTime.now();
        return date.isAfter(now.minusDays(90L));
    }

    // 계정이 잠겨 있지 않는지
    @Override
    public boolean isAccountNonLocked() {
        return !member.isLocked();
    }
}