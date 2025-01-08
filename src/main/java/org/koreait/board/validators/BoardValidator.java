package org.koreait.board.validators;

import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.RequestBoard;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Lazy
@Component
@RequiredArgsConstructor
public class BoardValidator implements Validator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestBoard.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) { // 에러가 있을경우
            return;
        }

        RequestBoard form = (RequestBoard) target; // RequestBoard를 타겟으로 지정
        // 비회원 비밀번호 없음
        if (!memberUtil.isLogin()) { // 로그인이 아닐때
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "guestPw", "NotBlank"); // 필드를 검증하여 비어있을경우 NotBlank 에러코드 반환
        }

        // 수정일 때 seq 필수 여부
        String mode = form.getMode();
        Long seq = form.getSeq();
        if (mode != null && mode.equals("edit") && (seq == null || seq < 1L)) {
            errors.rejectValue("seq", "NotNull");
        }
    }
}
