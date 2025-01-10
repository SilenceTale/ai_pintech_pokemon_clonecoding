package org.koreait.board.controllers;

import lombok.Data;
import org.koreait.global.paging.CommonSearch;

import java.util.List;

@Data
public class BoardSearch extends CommonSearch { // CommonSearch를 상속받음
    private List<String> bid; // 게시판 id
    private String sort; // 필드명_정렬방향  예) viewCount_DESC
    private List<String> email; // 회원 이메일
    private List<String> category; // 분류 조회
}
