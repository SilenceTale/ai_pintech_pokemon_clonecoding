package org.koreait.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CodeValue {
    @Id
    @Column(name="_CODE", length=45)
    private String code; // 범용적 형태의 저장공간을 만들었음.

    @Lob
    @Column(name="_VALUE")
    private String value; // 범용적 형태의 저장공간을 만들었음. JSON 형태의 문자로 변환
}
