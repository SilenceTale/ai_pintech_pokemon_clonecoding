package org.koreait.board.exceptions;

import org.koreait.global.exceptions.scripts.AlertBackException;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
