package org.koreait.global.paging;

import lombok.Data;

/**
 * 공통 검색 항목이다.
 */
@Data
public class CommonSearch {
    private int page = 1; // 기본값을 1페이지로 지정! 페이지 번호
    private int limit = 20; // 페이지 당 출력할 갯수, 20이면 1페이지당 20개의 데이터를 출력

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드
}
