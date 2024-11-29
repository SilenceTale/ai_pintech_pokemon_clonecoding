package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.member.constants.Authority;

@Data
@Entity
@IdClass(AuthoritiesId.class) // 이 어노테이션을 넣음으로써 아래의 데이터를 복합키로 설정
public class Authorities {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Authority authority;


}
