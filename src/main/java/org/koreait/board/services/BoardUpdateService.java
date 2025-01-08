package org.koreait.board.services;

import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.RequestBoard;
import org.koreait.board.entities.BoardData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardUpdateService {

    public BoardData process(RequestBoard form) {

        return null;
    }
}
