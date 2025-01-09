package org.koreait.board.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BoardView {
    @Id
    private Long seq; // 게시글 번호

    @Id
    private int hash; // 회원 번호 또는 IP + User-Agent로 조합해서 생성
}