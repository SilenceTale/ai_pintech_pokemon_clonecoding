package org.koreait.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.koreait.file.entities.FileInfo;

import java.util.List;

@Data
public class RequestBoard {

    private Long seq; // 게시글 번호
    private String mode;

    @NotBlank
    private String bid; // 게시판 id

    @NotBlank
    private String gid;

    @NotBlank
    private String poster; // 작성자
    private String guestPw; // 비회원 비밀번호

    @NotBlank
    private String subject; // 글 제목

    @NotBlank
    private String content; // 글 내용
    private boolean notice; // 공지글 여부

    private List<FileInfo> editorImages;
    private List<FileInfo> attachFiles;
}
