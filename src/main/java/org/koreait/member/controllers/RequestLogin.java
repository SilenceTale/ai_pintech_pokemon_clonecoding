package org.koreait.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestLogin implements Serializable {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String redirectUrl; // 로그인 완료 후 이동할 주소

    private List<String> errorCodes; // 에러코드를 직접 설정함으로 템플릿에 공통적인 기능들을 활용하기 위해서 에러 코드를 넣어주게 설정함.
    // 그중의 하나임.
}

