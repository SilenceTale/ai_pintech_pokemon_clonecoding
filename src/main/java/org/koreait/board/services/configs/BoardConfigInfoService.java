package org.koreait.board.services.configs;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.admin.board.controllers.BoardConfigSearch;
import org.koreait.admin.board.controllers.RequestBoard;
import org.koreait.board.entities.Board;
import org.koreait.board.entities.QBoard;
import org.koreait.board.exceptions.BoardNotFoundException;
import org.koreait.board.repositories.BoardRepository;
import org.koreait.global.paging.ListData;
import org.koreait.global.paging.Pagination;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Lazy // 필요할때 사용할 수 있도록 지연시킴
@Service // 이 클래스를 서비스 빈으로 등극
@RequiredArgsConstructor // 필수 의존성 주입을 위한 생성자 생성 어노테이션
public class BoardConfigInfoService {

    private final BoardRepository boardRepository;
    private final HttpServletRequest request;
    private final ModelMapper modelMapper;

    /**
     * 게시판 설정 하나 조회
     *
     * @param bid
     * @return
     */
    public Board get(String bid) {
        Board item = boardRepository.findById(bid).orElseThrow(BoardNotFoundException::new);// boardRepository에 있는 bid라는 Id 데이터로 Board 객체를 찾음, 그게 아니라면 BoardNotFoundException으로 예외처리함

        addInfo(item); // 추가 정보 처리

        return item; // Board의 item을 반환
    }

    public RequestBoard getForm(String bid) {
        Board item = get(bid);

        RequestBoard form = modelMapper.map(item, RequestBoard.class);
        form.setMode("edit");

        return form;
    }

    /**
     * 게시판 설정 목록
     * 
     * @param search
     * @return
     */
    public ListData<Board> getList(BoardConfigSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        BooleanBuilder andBuilder = new BooleanBuilder();
        QBoard board = QBoard.board;

        /* 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";
        if (StringUtils.hasText(skey)) {
            StringExpression condition;
            if (sopt.equals("BID")) { // 게시판 아이디
                condition = board.bid;
            } else if (sopt.equals("NAME")) { // 게시판명
                condition = board.name;
            } else { // 통합 검색 - 게시판 아이디 + 게시판명
                condition = board.bid.concat(board.name);
            }

            andBuilder.and(condition.contains(skey.trim()));
        }

        List<String> bids = search.getBid();
        if (bids != null && !bids.isEmpty()) { // bids가 null이 아니고 비어있지않을때 조건실행
            andBuilder.and(board.bid.in(bids)); // bids 조건이 들어있는 board.bid를 andBuilder로 조회
        }
        /* 검색 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt"))); // 한 페이지당 작성된 당시 내림차순으로 설정
        Page<Board> data = boardRepository.findAll(andBuilder, pageable); // boardRepository안에 있는 andBuilder, pageable 데이터를 Board객체에 리스트 형식으로 저장

        List<Board> items = data.getContent(); // Board리스트에 data 안에 있는 content데이터를 Board List의 items로 저장
        items.forEach(this::addInfo); //addInfo또한 items와 같이 설정

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(items, pagination);
    }

    // 추가 정보처리
    private void addInfo(Board item) {
        String category = item.getCategory();
        if (StringUtils.hasText(category)) { // category가 비어있는지 확인
            List<String> categories = Arrays.stream(category.split("\\n"))
                    .map(s -> s.replaceAll("\\r", ""))
                    .filter(s -> !s.isBlank())
                    .map(String::trim)
                    .toList(); // 비어있지않다면 리스트형태로 \r을 공백으로 전환후, 공백이 아닌걸 걸러 리스트형태로 반환

            item.setCategories(categories); // 최종적으로 변환된 categories를 board객체에 설정
        }
    }
}
