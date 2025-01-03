package org.koreait.message.controllers;

import lombok.Data;
import org.koreait.global.paging.CommonSearch;

import java.util.List;

@Data
public class MessageSearch extends CommonSearch {
    private List<String> sender; // 보낸쪽 이메일로 확인
    private String mode; // receive 이거나 값이 없으면 받은 쪽지, send : 보낸 쪽지만 가져온다.
}
