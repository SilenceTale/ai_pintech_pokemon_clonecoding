package org.koreait.message.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestMessage { // 커멘드 객체 생성

    private boolean notice;
    /**
     * 메일을 받는 쪽 이메일
     *      필수가 되는 조건 : 회원이 다른 회원에게 쪽지를 보내는 경우
     *      필수가 아닌 조건 : 관리자가 공지사항(notice 값이 true 인 경우)으로 쪽지를 보내는 경우
     */
    private String email;

    @NotBlank
    private String gid; // 그룹 id를 지정해서 파일을 보낼 수 있도록

    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}