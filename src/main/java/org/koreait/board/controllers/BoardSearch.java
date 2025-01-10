package org.koreait.board.controllers;

import lombok.Data;
import org.koreait.global.paging.CommonSearch;

import java.util.List;

@Data
public class BoardSearch extends CommonSearch { // 공통된 기능을 사용하기 위해 CommonSearch사용
    private List<String> bid; // 게시글 아이디
    private String sort; // 필드명_정렬방향  예) viewCount_DESC
    private List<String> email; // 회원 이메일
    private List<String> category; // 분류 조회
}
