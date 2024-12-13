package org.koreait.member.test.annotation;

import org.koreait.member.constants.Authority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockMember {
    long seq() default 1L; // 회원번호
    String email() default "user01@test.org"; // 테스트할 회원 이메일
    String password() default "_Aa123456"; // 테스트할 회원이메일의 비밀번호
    String name() default "사용자01"; // 회원 명
    String nickName() default "닉네임01"; // 회원의 닉네임(별명)
    Authority[] authority() default {Authority.USER}; // 권한(USER, MANAGER, ADMIN) 설정

}
