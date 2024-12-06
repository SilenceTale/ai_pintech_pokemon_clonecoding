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

        FileInfo item = infoService.get(seq);

        String filePath = item.getFilePath();
        String fileName = item.getFileName();
        // 윈도우에서 한글 깨짐 방지 [ ISO_8859_1 은 2 바이트이며, 한글이 안깨진다.]
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);

        String contentType = item.getContentType();
        // 마지막 주소를 기본 값으로
        contentType = StringUtils.hasText(contentType) ? contentType : "application/octet-stream";
        File file = new File(item.getFilePath());
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            // 바디의 출력을 filename에 지정된 파일로 지정한다. Content-Disposition은 가장 중요하다!
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType(contentType);
            response.setHeader("Cache-Control", "No-Cache");
            response.setHeader("pragma", "no-cache");
            response.setIntHeader("Expires", 0); // 만료시간을 없앤다.
            response.setContentLengthLong(file.length()); // 파일 용량을 알려준다.

            OutputStream out = response.getOutputStream();
            out.write(bis.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
