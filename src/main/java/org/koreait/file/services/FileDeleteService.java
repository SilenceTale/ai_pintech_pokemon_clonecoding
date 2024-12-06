package org.koreait.file.services;

import lombok.RequiredArgsConstructor;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.repositories.FileInfoRepository;
import org.koreait.global.exceptions.UnAuthorizedException;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor // 의존성 주입 어노테이션
public class FileDeleteService {
    private final FileInfoService infoService;
    private final FileInfoRepository infoRepository;
    private final MemberUtil memberUtil;

    public FileInfo delete(Long seq) {
        FileInfo item = infoService.get(seq);
        String filePath = item.getFilePath();
        // 0. 파일 소유자만 삭제 가능하게 통제 - 다만 관리자는 삭제 가능!
        String createdBy = item.getCreatedBy();
        // 관리자가 아닐때 && 회원일때 && 비회원이라면? || 로그인상태이긴하지만 이메일이 틀릴때 파일 소유자가 다르다.
        if(!memberUtil.isAdmin() && StringUtils.hasText(createdBy)
                && (!memberUtil.isLogin() || !memberUtil.getMember().getEmail().equals(createdBy))) {
            throw new UnAuthorizedException();
        }


        // 1. DB에서 정보를 제거합니다.
        infoRepository.delete(item);
        infoRepository.flush();

        // 2. 파일이 존재하면 파일도 삭제한다.
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        // 3. 삭제된 파일 정보를 반환한다.
        return item;
    }
    public List<FileInfo> deletes(String gid, String location) {
        List<FileInfo> items = infoService.getList(gid, location, FileStatus.ALL);
        items.forEach(i -> delete(i.getSeq()));

        return items;
    }

    public List<FileInfo> deletes(String gid) {
        return deletes(gid, null);
    }
}
