package org.koreait.file.services;


import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.exceptions.FileNotFoundException;
import org.koreait.file.repositories.FileInfoRepository;
import org.koreait.global.configs.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileInfoService  {
    private final FileInfoRepository infoRepository;

    private final FileProperties properties;

    public FileInfo get(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow(FileNotFoundException::new);

        addInfo(item); // 추가 정보 처리

        return item;
    }

    /**
     * 추가 정보 처리
     *
     * @param item
     */
    public void addInfo(FileInfo item) {
        // filePath - 서버에 올라간 실제 경로 ( 다운로드나 삭제시 활용한다.

        // fileUrl - 접근할 수 있는 어떠한 주소.(브라우저에 입력하는 주소)
    }

    public String getFilePath(FileInfo item) {

    }

    public String getFileUrl(FileInfo item) {

    }
}
