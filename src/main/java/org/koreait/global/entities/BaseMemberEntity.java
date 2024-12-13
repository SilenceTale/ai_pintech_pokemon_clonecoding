package org.koreait.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass // 엔티티클래스 중 가장 상위클래스를 알려주는 것이다. 상속을 통해서 사용하는 공통 기능
@EntityListeners(AuditingEntityListener.class) // 이 엔티티가 계속 작동하는지 감시하기위해작동, 변경이돼거나 추가가됐다면 코드를 넣어주는 역할.
public abstract class BaseMemberEntity extends BaseEntity {

    @CreatedBy
    @Column(length=65, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(length=65, insertable = false)
    private String modifiedBy;
}
