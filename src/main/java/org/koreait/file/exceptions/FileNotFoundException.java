package org.koreait.file.exceptions;

import org.koreait.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends AlertBackException {
    public FileNotFoundException() {
        super("NotFound.file", HttpStatus.NOT_FOUND); // 응답코드는 404로 고정시켜서 오류 코드를 보냄
        setErrorCode(true);
    }
}
