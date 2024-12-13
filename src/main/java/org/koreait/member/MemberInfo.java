package org.koreait.member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.koreait.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Builder
@ToString
public class MemberInfo implements UserDetails { // UserDetails를 꼭 구현해야함.

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LocalDateTime credentialChangedAt = member.getCredentialChangedAt();
        return credentialChangedAt != null &&
                credentialChangedAt.isAfter(LocalDateTime.now().minusMonths(1L));
        //지난 비번 변경 시간을 체크하고 30일이 되지 않았다면 이용이 가능하지만 넘는다면 비밀번호 변경창으로 넘어갈 수 있도록 기능을 구현
    }

    @Override
    //isEnabled가 false라면 disabledException이 발생함!
    public boolean isEnabled() { // 회원 탈퇴 여부
        return member.getDeletedAt() == null; // null이면 탈퇴한것. true도 마찬가지이지만 false일 경우 탈퇴한 것으로 표현함.
    }
}
