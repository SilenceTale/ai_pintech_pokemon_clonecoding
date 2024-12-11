package org.koreait.global.paging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class Pagination {

    private int page;
    private int total;
    private int ranges;
    private int limit;
    private int totalPages;
    private int firstRangePage;
    private int lastRangePage;
    private int prevRangeLastPage;
    private int nextRangeFirstPage;

    private String baseUrl;

    /**
     *
     * @param page : 현재 페이지 번호
     * @param total : 총 레코드 갯수
     * @param ranges : 페이지 구간 갯수
     * @param limit : 한 페이지 당 출력될 레코드 갯수
     */
    public Pagination(int page, int total, int ranges, int limit) {
        this(page, total, ranges, limit, null);
    }

    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request) {
        // 페이징 기본값 처리
        page = Math.max(page, 1);
        total = Math.max(total, 0); // total 갯수가 없으면 페이지를 만들 수 없다.
        ranges = ranges < 1 ? 10 : ranges; // 페이지 밑에 보일 갯수를 설정한다.
        limit = limit < 1 ? 20 : limit;

        if (total == 0) {
            return;
        }

        // 전체 페이지 갯수
        int totalPages = (int)Math.ceil(total / (double)limit); // 이부분은 12-11 오후 12:09분으로 다시 주석으로 설명하자.

        // 구간 번호 - 0, 1, 2
        int rangeCnt = (page - 1) / ranges;
        int firstRangePage = rangeCnt * ranges + 1; // 현재 구간의 시작 페이지 번호
        int lastRangePage = firstRangePage + ranges - 1; // 현재 구간의 종료번호, 마지막 페이지 번호
        lastRangePage = Math.min(lastRangePage, totalPages);

        int prevRangeLastPage = 0, nextRangeFirstPage = 0; // 이전 구간 시작 페이지 번호, 다음 구간 시작 페이지 번호
        if (rangeCnt > 0) {
            prevRangeLastPage = firstRangePage - 1;
        }

        // 마지막 페이지 구간
        int lastRangegCnt = (totalPages - 1) / ranges;
        if (rangeCnt < lastRangegCnt) {
            nextRangeFirstPage = (rangeCnt + 1) * ranges + 1;
        }
        /* 쿼리스트링 값 처리 S */
        String qs = request.getQueryString();
        baseUrl = "?";
        if (StringUtils.hasText(qs)) {
            baseUrl += Arrays.stream(qs.split("&"))
                    .filter(s -> !s.contains("page="))
                    .collect(Collectors.joining("&")) + "&";
        }
        baseUrl += "page=";
        /* 쿼리 스트링 값 처리 E */

        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
        this.totalPages = totalPages;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.prevRangeLastPage = prevRangeLastPage;
        this.nextRangeFirstPage = nextRangeFirstPage;
    }
    /**
     * String 배열의 0번째 - 페이지 번호 숫자, 1번째 - 페이지 URL
     * @return
     */
    public List<String[]> getPages() {
        if (total == 0) {
            return Collections.EMPTY_LIST;
        }

        List<String[]> pages = new ArrayList<>();
        for (int i = firstRangePage; i <= lastRangePage; i++) {
            String url = baseUrl + i;
            pages.add(new String[] {"" + i, url});
        }

        return pages;
    }
}
