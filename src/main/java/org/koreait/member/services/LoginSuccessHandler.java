package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler { //스프링 시큐리티에 이미 정해진 인터페이스. 성공시에 뭘 할지 정의를 내려야함.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        //유저 구현체 등을 성공시에도 사용하는것이 Authentication. 이 코드는 로그인이 성공, 실패했을 시 사용하게 됀다.

        // requestLogin 세션값 비우기
        session.removeAttribute("requestLogin"); // 비웠을시 로그인 성공시 유입됐을때 실패했을때 에러메세지라던지, 어떠한 문제가 생기지않기 위해서.

        // UserDetails 구현체

        //MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal();
        //System.out.println(memberInfo);

        /**
         * 로그인 성공시 페이지 이동
         * 1) redirectUrl에 지정된 주소
         * 2) redirectUrl이 없는 경우는 메인 페이지 이동
         * 아래의 코드를 꼭 올바르게 해석하고 기억할 것.
         */

        String redirectUrl = request.getParameter("redirectUrl");
        redirectUrl = StringUtils.hasText(redirectUrl) ? redirectUrl : "/";

        response.sendRedirect(request.getContextPath() + redirectUrl);

    }
}
