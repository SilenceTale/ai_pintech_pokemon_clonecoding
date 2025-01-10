package org.koreait.koreait.wishlist.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.member.entities.Member;
import org.koreait.wishlist.constants.WishType;
import org.koreait.wishlist.entities.WishId;

@Data
@Entity
@IdClass(WishId.class)
public class Wish {
    @Id
    private Long seq;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length=15, name="_type")
    private WishType type;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
