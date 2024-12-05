package org.koreait.file.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.koreait.global.entities.BaseMemberEntity;

@Data
@Entity
public class FileInfo extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long seq; // 파일 등록 번호 (기본키 설정하기)

    private String gid; // 파일 그룹
    private String location; // 그룹 내에서 위치

    private String fileName; // 업로드시 원 파일명
    private String extension; // 파일 확장자
    private String contentType; // 파일 형식, image/png     application/...

    private boolean done; // 파일과 연관된 작업이 완료 되었는지 여부 체크
}
