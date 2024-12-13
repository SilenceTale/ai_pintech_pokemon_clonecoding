package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
// 스프링 데이터 쪽 기술
@Lazy
@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> { // AuditorAware 인터페이스를 기억해두기.

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {

        String email = null;
        if (memberUtil.isLogin()) {
            email = memberUtil.getMember().getEmail();
        }

        return Optional.ofNullable(email);
    }
}
