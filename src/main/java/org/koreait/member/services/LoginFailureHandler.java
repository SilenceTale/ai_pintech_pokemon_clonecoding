package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.member.controllers.RequestLogin;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    // AuthenticationFailureHandler 인터페이스는 설정 인터페이스.
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();
        RequestLogin form = Objects.requireNonNullElse((RequestLogin)session.getAttribute("requestLogin"), new RequestLogin());
        form.setErrorCodes(null); // 처음에 에러코드를 초기화를 시킨다, 왜? 세션범이로 바꿧기때문에 유지가 돼기에, 다시 들어오면 기존 데이터가 남아서 다시 검증하기 때문에 그 코드를 초기화.
        // 추가한 이유는 커맨드 객체 검증을 최대한 활용하기 위해서 밑에 로그인은 세션쪽으로 넘어가도록 설정함.
        // MemberController로 넘어가면 @SessionAttribute 안에 "requestLogin"이 들어가 있음.

        String email = request.getParameter("email"); // 검증을 위해서 데이터를 추가함.
        String password = request.getParameter("password"); // 검증을 위해서 데이터를 추가함.

        form.setEmail(email);
        form.setPassword(password);

        String redirectUrl = request.getContextPath() + "/member/login"; // 오류가 발생한다면 로그인 페이지로 넘억도록 설정. 그것을 ContextPath() 문장으로 완성. 즉 검증 준비단계를 완료시키기 위해서.

        // 아이디 또는 비밀번호를 입력하지 않은 경우, 아이디로 조회 X, 비번이 일치하지 않는 경우
        if (exception instanceof BadCredentialsException) { // badcredentialsException은 AuthenticationException의 하위 객체.
            List<String> errorCodes = Objects.requireNonNullElse(form.getErrorCodes(), new ArrayList<>());

            if (!StringUtils.hasText(email)) {
                errorCodes.add("NotBlank_email"); // 이 코드가 추가돼어 있다면 필수 항목을 검증한다.
            }

            if (!StringUtils.hasText(password)) {
                errorCodes.add("NotBlank_password"); // 이 코드가 추가돼어 있다면 필수 항목을 검증한다.
            }


            if (errorCodes.isEmpty()) {
                errorCodes.add("Failure.validate.login"); // 이메일 이고, 비번이 있는데 틀린다면? 다시 검증을 통해 에러 코드를 validate에 넣어서 보여준다.
            }

            form.setErrorCodes(errorCodes);
        } else if (exception instanceof CredentialsExpiredException) { //  비밀번호가 만료된 경우
            redirectUrl = request.getContextPath() + "/member/password/change"; // credentialChange가 발생한다면 비밀번호 변경을 하라고 전달하기 위해 주소를 추가함.
        } else if (exception instanceof DisabledException) { // 탈퇴한 회원
            form.setErrorCodes(List.of("Failure.disabled.login"));
        }


        session.setAttribute("requestLogin", form);

        // 로그인 실패시에는 로그인 페이지로 이동
        response.sendRedirect(redirectUrl);
    }
}
