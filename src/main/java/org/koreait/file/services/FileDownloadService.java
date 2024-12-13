package org.koreait.file.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.exceptions.FileNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Lazy
@Service
@RequiredArgsConstructor
public class FileDownloadService {
    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void process(Long seq) {

        FileInfo item = infoService.get(seq); // 기초데이터는 FileInfo 안에 들어가 있다.


        String fileName = item.getFileName();
        // 윈도우에서 한글 깨짐 방지
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);

        String contentType = item.getContentType();
        contentType = StringUtils.hasText(contentType) ? contentType : "application/octet-stream"; // 가상의 주소를 기본 값으로 주입시킴.

        File file = new File(item.getFilePath());
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        try (FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis)) {
            // 바디의 출력을 filename에 지정된 파일로 변경 | Content-Disposition이 가장 중요한 헤더!
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType(contentType);
            response.setHeader("Cache-Control", "no-cache"); // 캐시가 남아있다면 전에 설치한 파일이 재설치돼기에 캐시 사용을 불가로.
            response.setHeader("Pragma", "no-cache");
            response.setIntHeader("Expires", 0); // 만료시간을 없앤다.
            response.setContentLengthLong(file.length());

            OutputStream out = response.getOutputStream();
            out.write(bis.readAllBytes()); // 바이트 형태로 파일을 작성

        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}
