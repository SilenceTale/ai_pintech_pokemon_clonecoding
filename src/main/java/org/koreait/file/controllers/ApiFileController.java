package org.koreait.file.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.global.libs.Utils;
import org.koreait.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController {

    private final Utils utils;

    @PostMapping("/upload")
    public JSONData upload() {

        return null;
    }

    // 파일 다운로드
    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) {

    }

    // 파일 단일 조회
    @GetMapping("/info/{seq}")
    public JSONData info(Long seq) {

        return null;
    }

    /**
     * 파일 목록 조회
     * gid, location
     */
    @GetMapping(path = {"/list/{gid}", "/list/{gid}/{location}"})
    public JSONData list(@PathVariable("gid") String gid,
                         @PathVariable(name="location", required = false) String location) {

        return null;
    }

    // 파일 당일 삭제
    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {

        return null;
    }

    @DeleteMapping({"/delete/{gid}", "/delete/{gid}/{location}"})
    public JSONData deletes(@PathVariable("gid") String gid,
                            @PathVariable(name = "location", required = false) String location) {

        return null;
    }
}
