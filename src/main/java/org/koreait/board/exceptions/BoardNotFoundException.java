package org.koreait.board.exceptions;

import org.koreait.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        super("NotFound.board", HttpStatus.NOT_FOUND);
        setErrorCode(true); // board에 데이터가 없다면 NotFound에러코드 발송
    }
}
