package org.koreait.board.services.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.board.controllers.BoardConfigSearch;
import org.koreait.board.entities.Board;
import org.koreait.board.repositories.BoardRepository;
import org.koreait.global.paging.ListData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {
    private final BoardRepository boardRepository;

    /**
     * 게시판 설정 하나 조회
     *
     * @param bid
     * @return
     */
    public Board get(String bid) {
        Board item = boardRepository.findById(bid).orElseThrow(BoardNotFoundException::new);

        addInfo(item); // 추가 정보 처리

        return item;
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
        

        return null;
    }

    private void addInfo(Board item) {

    }
}