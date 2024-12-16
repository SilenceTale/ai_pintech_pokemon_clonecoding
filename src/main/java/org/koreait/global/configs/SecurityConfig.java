package org.koreait.global.configs;

import org.koreait.member.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 스프링 시큐리티 설정
 *
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private MemberInfoService memberInfoService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 이쪽 기능이 제일 중요함.
        // 왜? -> 필터체인을 걸어서 인증 설정을 볼 수 있음.

        /* 인증 설정 S - 로그인, 로그아웃 */
        http.formLogin(c -> {
           c.loginPage("/member/login") // 로그인 양식을 처리할 주소
                   .usernameParameter("email") // 바뀔 수 있으므로 설정이 가능함. id, account, email 등으로 설정이 가능하듯...
                   .passwordParameter("password")
                   .failureHandler(new LoginFailureHandler())
                   .successHandler(new LoginSuccessHandler());
        });

        http.logout(c -> {
           c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 주소를 입력하면 입력한 주소로 로그아웃을 진행
                   .logoutSuccessUrl("/member/login");
        });
        /* 인증 설정 E */

        /* 인가 설정 S - 페이지 접근 통제 */
        /**
         * authenticated() : 인증받은 사용자만 접근
         * anonymous() : 인증 받지 않은 사용자만 접근
         * permitAll() : 모든 사용자가 접근 가능
         * hasAuthority("권한 명칭") : 하나의 권한을 체크
         * hasAnyAuthority("권한1", "권한2", ...) : 나열된 권한 중 하나라도 충족하면 접근 가능
         * ROLE
         * ROLE_명칭
         * hasRole("명칭")
         * hasAnyRole(...)
         */
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated() // 인증한 회원 
                    .requestMatchers("/member/login", "/member/join", "/member/agree").anonymous() // 미인증 회원
                    .requestMatchers("/admin/**").hasAnyAuthority("MANAGER", "ADMIN") // 관리자 페이지는 MANAGER, ADMIN 권한
                    .anyRequest().permitAll(); // 나머지 페이지는 모두 접근 가능
        });

        http.exceptionHandling(c -> {
            c.authenticationEntryPoint(new MemberAuthenticationExceptionHandler())  // 미로그인시 인가 실패
                    .accessDeniedHandler(new MemberAccessDeniedHandler()); // 로그인 이후 인가 실패 -> 403에러가 떠야하지만 우리는 401 에러가 뜨도록 설정함.

        });

        /* 인가 설정 E */

        /* 자동 로그인 설정 S */
        http.rememberMe(c -> {
           c.rememberMeParameter("autoLogin")
                   .tokenValiditySeconds(60 * 60 * 24 * 30) // 자동 로그인을 유지할 시간, 14일이 default값이다
                   .userDetailsService(memberInfoService)
                   .authenticationSuccessHandler(new LoginSuccessHandler());
        });
        /* 자동 로그인 설정 E */

        return http.build(); // 처음 코드 사용시 설정 무력화였지만 지금은 설정 객체를 빌드로 만들어서 내보내는 역할.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
