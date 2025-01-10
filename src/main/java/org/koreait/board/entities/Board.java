package org.koreait.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseMemberEntity;
import org.koreait.member.constants.Authority;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Board extends BaseMemberEntity implements Serializable {
    @Id
    @Column(length=30)
    private String bid;

    @Column(length=90, nullable = false)
    private String name; // 게시판명

    private boolean open;

    @Lob
    private String category;

    private int rowsPerPage;
    private int pageRanges;
    private int pageRangesMobile;

    private boolean useEditor;
    private boolean useEditorImage;
    private boolean useAttachFile;
    private boolean useComment; // 댓글 사용 여부
    private String locationAfterWriting; // 글 작성후 이동 경로 - list : 목록, view : 글보기

    private String skin;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority listAuthority;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority viewAuthority;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority writeAuthority;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority commentAuthority;

    @Transient
    private List<String> categories;
}

/*

@Data
@Entity
public class Board extends BaseMemberEntity implements Serializable {
    @Id // 기본키로 지정하는 어노테이션
    @Column(length=30)
    private String bid; // board id -> 게시판 아이디

    @Column(length=90, nullable = false) // 글자수 90, null값 허용X, 필수로 들어가야하는 값
    private String name; // 게시판명

    private boolean open; // 공개 비공개를 설정함

    @Lob // LargeObject, 대용량 텍스트 파일을 정리하는데 사용하는 어노테이션
    private String category; // 카테고리 설정 여부

    private int rowsPerPage; // 세로로 게시글을 보기위한 페이지 의존성
    private int pageRanges; // 한 페이지에 게시글을 얼마나 볼지 거리 조정한 어노테이션
    private int pageRangesMobile; // 모바일에서 한 페이지당 게시글을 얼마나 볼지 거리 조정한 어노테이션

    private boolean useEditor; // 에디터 사용 여부
    private boolean useEditorImage; // 이미지 파일 올리기 여부
    private boolean useComment; // 댓글 사용 여부
    private String locationAfterWriting; // 글 작성후 이동 경로 - list : 목록, view : 글보기로 의존성 추가

    private String skin;

    @Enumerated(EnumType.STRING) // 열거형 타입을 지정하기 위해 사용하는 어노테이션
    @Column(length=20, nullable = false)
    private Authority listAuthority; // 게시판 보는 권한 설정

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority viewAuthority; // 게시글 보는 권한 설정

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority writeAuthority; // 게시글 작성 권한 설정

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false) // 글자수 20자, 비어있는 값으로 남기기 불가
    private Authority commentAuthority; // 댓글 작성 권한 설정

    @Transient // DB에 저장돼지 않도록 일시적으로 해제한 데이터
    private List<String> categories; // 카테고리들을 리스트값으로 받아옴
}
*/
