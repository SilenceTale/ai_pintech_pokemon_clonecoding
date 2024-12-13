package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class MemberAuthenticationExceptionHandler implements AuthenticationEntryPoint { // 인가 실패시 넘어오는 클래스.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /**
         * 마이페이지 /mypage -> 로그인 페이지
         * 관리자 : 응답 코드 401
         */
        String uri = request.getRequestURI(); // 요청 주소
        if (uri.contains("/mypage")) { // 마이페이지
            response.sendRedirect(request.getContextPath() + "/member/login?redirectUrl=/mypage"); // 로그인이 완료돼면 mypage 주소란으로 넘어갈 수 있도록 설정. 현장에서도 많이 쓰는 패턴이기에 외워야함.
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 그외 401 // 이부분도 기억하기.
        }
        
    }
}
