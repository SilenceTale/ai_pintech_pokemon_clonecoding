package org.koreait.admin.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.koreait.member.constants.Authority;

@Data
public class RequestBoard {

    private String mode;

    @NotBlank
    private String bid; // 게시판 아이디

    @NotBlank
    private String name; // 게시판 명

    /* 줄개행 문자로 여러 분류를 등록
    * 1페이지당 게시글 갯수
    * front 뷰일때 노출되는 페이지 링크 갯수
    * mobile 뷰일때 노출되는 페이지 링크 갯수
    * mobile 뷰일때 노출되는 페이지 링크 갯수
    * 에디터 사용 여부
    * 에디터 첨부 이미지 사용 여부
    * 파일 첨부 사용 여부
    * 댓글 사용 여부
    * 글 작성 후 이동 경로 list, view
    * 게시판 스킨
    */
    private boolean open;
    private String category;
    private int rowsPerPage;
    private int pageRanges;
    private int pageRangesMobile;
    private boolean useEditor;
    private boolean useEditorImage;
    private boolean useAttachFile;
    private boolean useComment;
    private String locationAfterWriting;
    private String skin; // 게시판 스킨

    /**
     * ALL - 비회원 + 회원 + 관리자
     * USER - 회원 + 관리자
     * ADMIN - 관리자
     */
    private Authority listAuthority; // 목록 접근 권한
    private Authority viewAuthority; // 글보기 접근 권한
    private Authority writeAuthority; // 글쓰기, 수정, 삭제 권한
    private Authority commentAuthority; // 댓글 작성 권한

}
