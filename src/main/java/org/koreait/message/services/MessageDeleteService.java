package org.koreait.message.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.exceptions.UnAuthorizedException;
import org.koreait.member.libs.MemberUtil;
import org.koreait.message.entities.Message;
import org.koreait.message.repositories.MessageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Lazy
@Service
@RequiredArgsConstructor
public class MessageDeleteService {
    private final MessageInfoService infoService;
    private final MessageRepository repository;
    private final MemberUtil memberUtil;

    /**
     * 삭제 처리
     * 0. 공지인 경우는 관리자인 경우만 삭제 가능
     * 1. sender 쪽에서 삭제하는 경우 / mode 값이 send 일때만!
     *      deletedBySender 값을 true
     * 2. receiver 쪽에서 삭제하는 경우 / mode 값이 receive 일때만!
     *      deletedByReceiver 값을 true
     * 3. deletedBySender 와 deletedByReceiver 가 모두 true 인 경우 실제 DB에서도 삭제(Message 쪽 삭제, 파일 데이터 함께 삭제)
     *
     *
     * @param seq
     */
    public void process(Long seq, String mode) {
        mode = StringUtils.hasText(mode) ? mode : "receive";

        boolean isProceedDelete = false;
        Message item = infoService.get(seq);
        if (item.isNotice()) {
            if (memberUtil.isAdmin()) { // 삭제 처리
                isProceedDelete = true;
            } else { // 공지이지만 관리자가 아닌 경우 - 권한 없음 예외처리로 던지기
                throw new UnAuthorizedException();
            }
        } // ..endif

        
    }
}
