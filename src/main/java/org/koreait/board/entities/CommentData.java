package org.koreait.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.entities.Member;

@Data
@Entity
public class CommentData extends BaseEntity {
    @Id @GeneratedValue
    private Long seq; // 수정할때만 필수로 검증할 수 있게 넣음

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardData Data; // 한개의 데이터에 여러 데이터가 들어오기에 ManyToOne 사용

    @Column(length = 40, nullable = false)
    private String commenter; // 댓글 작성자

    @Column(length = 65)
    private String guestPw; // 비회원 비밀번호

    @Lob
    @Column(nullable = false)
    private String Content; //댓글 내용

    @Column(length = 20)
    private String IpAddr;

    @Column(length = 150)
    private String UserAgent; // 브라우저에 관한 기록
}
