package org.koreait.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestComment {
    private String mode;
    private Long seq;

    @NotBlank
    private String Commenter; // 댓글 다는 사용자

    private String guestPw; // 비회원 비밀번호

    @NotBlank
    private String Content; // 댓글 내용
}
