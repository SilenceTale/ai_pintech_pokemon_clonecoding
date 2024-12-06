package org.koreait.mypage.controllers;

import lombok.Data;

@Data
public class RequestProfile {

    private String name; // 회원 명 변경코드
    private String nickname; // 회원 별명(닉네임) 변경 코드
    private String Password; // 비밀번호 변경 코드
    private String confirmPassword; // 비밀번호 확인 변경 코드.
}
