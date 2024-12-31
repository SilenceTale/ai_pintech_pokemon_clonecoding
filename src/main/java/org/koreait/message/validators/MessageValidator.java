package org.koreait.message.validators;

import lombok.RequiredArgsConstructor;
import org.koreait.member.libs.MemberUtil;
import org.koreait.message.controllers.RequestMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Lazy
@Component
@RequiredArgsConstructor
public class MessageValidator implements Validator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestMessage.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestMessage form = (RequestMessage) target;
        String email = form.getEmail();
        boolean notice = form.isNotice();
        if (!memberUtil.isAdmin() && notice) { // 관리자가 아닌데, 공지 쪽지일 경우 X
            notice = false;
            form.setNotice(notice);
        }

        if (!memberUtil.isAdmin() && !notice && !StringUtils.hasText(email)) { // 관리자가 아니고 공지글이 아닌데 이메일도 없을 경우
            errors.rejectValue("email", "NotBlank"); // 이메일 칸을 공백으로 둘 수 없게 설정

        }
    }
}
