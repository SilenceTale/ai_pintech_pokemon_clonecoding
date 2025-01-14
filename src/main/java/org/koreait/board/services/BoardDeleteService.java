package org.koreait.board.services;

import lombok.RequiredArgsConstructor;
import org.koreait.board.repositories.BoardRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardDeleteService {
    private final BoardInfoService infoService;
    private final BoardRepository boardRepository;
}
