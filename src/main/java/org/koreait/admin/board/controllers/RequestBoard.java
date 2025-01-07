package org.koreait.admin.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.koreait.member.constants.Authority;

@Data
public class RequestBoard {

    private String mode;

    @NotBlank
    private String bid; // 게시판 id

    @NotBlank
    private String name; // 게시판 명

    private boolean open; // 공개 글인지 비공개 글인지 설정
    private String category; // 줄 개행 문자로 여러 분류를 등록한다
    private int rowsPerPage; // 1페이지당 게시글 갯수
    private int pageRanges; // pc 기준 Front 뷰일때 노출되는 페이지 링크 갯수
    private int pageRangesMobile; // 모바일 기준 Front 뷰일때 노출되는 페이지 링크 갯수
    private boolean useEditor; // 에디터 사용 여부 체크
    private boolean useEditorImage; // 에디터 첨부 이미지 노출 사용 여부 체크
    private boolean useAttachFile; // 다운로드된 첨부 파일 사용 여부 체크
    private boolean useComment; // 댓글 사용 여부 체크
    private String skin; // 게시판 스킨

    /**
     * ALL - 비회원 + 회원 + 관리자
     * USER - 회원 + 관리자
     * ADMIN - 관리자
     */
    private Authority listAuthority; // 목록 접근 권한 설정
    private Authority viewAuthority; // 글 보기 접근 권한 설정
    private Authority writeAuthority; // 글 쓰기, 수정, 삭제 권한 설정
    private Authority commentAuthority; // 댓글 작성, 삭제 권한 설정
}
