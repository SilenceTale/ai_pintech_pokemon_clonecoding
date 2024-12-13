package org.koreait.global.libs;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.services.FileInfoService;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils { // 편의기능 클래스

    private final HttpServletRequest request;
    private final MessageSource messageSource; // 2. 그렇게 사용하기 위해 messageSource를 주입함.
    private final FileInfoService fileInfoService;

    public boolean isMobile() {

        // 요청 헤더 - User-Agent / 브라우저 정보
        String ua = request.getHeader("User-Agent"); // 브라우저의 정보를 요청 헤더를 통해 확인.
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*"; //isMobile을 구현하는 패턴


        return StringUtils.hasText(ua) && ua.matches(pattern);
    }

    /**
     * mobile, front 템플릿 분리 함수
     *
     * @param path
     * @return
     */
    public String tpl(String path) {
        String prefix = isMobile() ? "mobile" : "front"; // 모바일인지 PC인지 구분짓도록 조건식을 걺

        return String.format("%s/%s", prefix, path);
    }

    /**
     * 메서지 코드로 조회된 문구
     *
     * @param code
     * @return
     */
    public String getMessage(String code) {
        Locale lo = request.getLocale(); // 사용자 요청 헤더(Accept-Language)
        //요청헤더(Accept-Language)의 정보를 바탕으로 브라우저의 언어설정을 가져옴
        return messageSource.getMessage(code, null, lo); // 메세지 코드 형식으로 전달을 하기 위해 반환값을 messageSource로 지정.
    }

    public List<String> getMessages(String[] codes) { // 코드가 여러개 있을때 한꺼번에 처리하기 위해서 getMessages사용!

        return Arrays.stream(codes).map(c -> {
            try {
                return getMessage(c);
            } catch (Exception e) {
                return "";
            }
        }).filter(s -> !s.isBlank()).toList(); // 필터로 거르고 리스트를 설정함.

    }

    /**
     * REST 커맨드 객체 검증 실패시에 에러 코드를 가지고 메세지 추출
     *
     * @param errors
     * @return
     */
    public Map<String, List<String>> getErrorMessages(Errors errors) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false); // 에러형태의 메세지만 보려고하기에 false값으로 설정.
        try {
            // 필드별 에러코드 - getFieldErrors()
            // Collectors.toMap
            Map<String, List<String>> messages = errors.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, f -> getMessages(f.getCodes()), (v1, v2) -> v2));

            // 글로벌 에러코드 - getGlobalErrors()
            List<String> gMessages = errors.getGlobalErrors()
                    .stream()
                    .flatMap(o -> getMessages(o.getCodes()).stream())
                    .toList();
            // 글로벌 에러코드 필드 - global
            if (!gMessages.isEmpty()) {
                messages.put("global", gMessages);
            }

            return messages;
        } finally {
            ms.setUseCodeAsDefaultMessage(true); // 싱글턴 객체로인해서 다 사용했기에 다시 true값으로 반환시키겠끔.
        }
    }

    /**
     * 이미지 출력
     *
     * @param width
     * @param height
     * @param mode - image : 이미지 태그로 출력, background : 배경 이미지 형태 출력
     * @return
     */
    public String showImage(Long seq, int width, int height, String mode, String className) {
        return showImage(seq, null, width, height, mode, className);
    }

    public String showImage(Long seq, int width, int height, String className) {
        return showImage(seq, null, width, height, "image", className);
    }

    public String showBackground(Long seq, int width, int height, String className) {
        return showImage(seq, null, width, height, "background", className);
    }

    public String showImage(String url, int width, int height, String mode, String className) {
        return showImage(null, url, width, height, mode, className);
    }

    public String showImage(String url, int width, int height, String className) {
        return showImage(null, url, width, height, "image", className);
    }

    public String showBackground(String url, int width, int height, String className) {
        return showImage(null, url, width, height, "background", className);
    }

    public String showImage(Long seq, String url, int width, int height, String mode, String className) {

        try {
            String imageUrl = null;
            if (seq != null && seq > 0L) {
                FileInfo item = fileInfoService.get(seq);
                if (!item.isImage()) {
                    return "";
                }

                imageUrl = String.format("%s&width=%d&height=%d", item.getThumbUrl(), width, height);

            } else if (StringUtils.hasText(url)) {
                imageUrl = String.format("%s/api/file/thumb?url=%s&width=%d&height=%d", request.getContextPath(), url, width, height); // 원격 이미지 주소를 받고 ??
            }

            if (!StringUtils.hasText(imageUrl)) return "";

            mode = Objects.requireNonNullElse(mode, "image");
            className = Objects.requireNonNullElse(className, "image");
            if (mode.equals("background")) { // 배경 이미지

                return String.format("<div style='width: %dpx; height: %dpx; background:url(\"%s\") no-repeat center center; background-size:cover; class='%s'></div>", width, height, imageUrl, className);
            } else { // 이미지 태그
                return String.format("<img src='%s' class='%s'>", imageUrl, className);
            }
        } catch (Exception e) {}

        return "";
    }
}