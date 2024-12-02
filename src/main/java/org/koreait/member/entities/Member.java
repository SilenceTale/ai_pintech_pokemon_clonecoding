package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Gender;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long seq; // 회원번호

    @Column(length = 65, nullable = false, unique = true)
    private String email; // 이메일

    @Column(length = 65, nullable = false)
    private String password; // 비밀번호

    @Column(length = 40, nullable = false)
    private String name; // 이름

    @Column(length = 40, nullable = false) // 별칭의 길이 10글자 이하, null? : false로 거부
    private String nickName; // 별칭

    @Column(nullable = false)
    private LocalDate birthDt; // 생년월일

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender; // 성별

    @Column(length = 10, nullable = false)
    private String zipCode; // 우편주소

    @Column(length = 100, nullable = false)
    private String address; // 도로명 주소

    @Column(length = 100)
    private String addressSub; // 세부 주소

    private boolean requiredTerms1; // 약관동의 1

    private boolean requiredTerms2; // 약관동의 2

    private boolean requiredTerms3; // 약관동의 3

    @Column(length = 50)
    private String optionalTerms; // 선택약관

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Authorities> authorities;
}
