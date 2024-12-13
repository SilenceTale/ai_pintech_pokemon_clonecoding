package org.koreait.global.advices;

import lombok.RequiredArgsConstructor;
import org.koreait.global.exceptions.CommonException;
import org.koreait.global.libs.Utils;
import org.koreait.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class CommonRestControllerAdvice {

    private final Utils utils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData> errorHandler(Exception e) { // 에러 코드에 관한 코드 부분. 에러페이지 보여주는?
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 기본 에러 코드 500

        Object message = e.getMessage();

        if (e instanceof CommonException commonException) {
            status = commonException.getStatus();

            Map<String, List<String>> errorMessages = commonException.getErrorMessages(); // 2차가공을 통해서 현재에 대한 값을 넣어줌.
            if (errorMessages != null) {
                message = errorMessages; //JSon형태로 에러 메세지? 이 코드가 없으면 기본 에러 메세지로 보내줌.
            } else {
                message = commonException.isErrorCode() ? utils.getMessage((String)message) : message;
            }
        }

        JSONData data = new JSONData(); // 예측이 가능한 응답코드를 건내주기 위해서.
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(message);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data); // 응답코드와 JSON데이터로 바디데이터를 가공해서 넘겨줌
    }
}
