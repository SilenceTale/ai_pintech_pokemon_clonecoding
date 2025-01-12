package org.koreait.board.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode // 동등한 hash값을 자동으로 메서드를 추가 하기 위한 어노테이션 equals() HashCode()
@AllArgsConstructor // 모든 필드를 매개 변수로 만들어주는 어노테이션
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성하는 어노테이션
public class BoardViewId {
    private Long seq; // Long 자료형으로 seq 값을 받아들임
    private int hash; // int 자료형으로 hash값을 받아들임
}
