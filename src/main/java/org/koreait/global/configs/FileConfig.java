package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class) // 의존성 주입 방식으로 사용하기 위해서, class클래스 객체를 넣음으로써 사용한다.
public class FileConfig implements WebMvcConfigurer { // 2.인터페이스를 불러온 이유는 필요한 정적 파일의 경로를 연결하기 위해서이면서도  WebMvcConfigurer를 사용하기 위해서, 그 범주 안에 들어가 있는 WebMvcConfigurer 인터페이스를 불러옴.

    private final FileProperties properties; // 서버쪽과 분리해서 사용하기 위함. 설정파일에 있는 값을 가져오기 위해서 지정하기도했다. 조금 더 범주화 해서 파일쪽 설정들을 가져오기위해 데이터 클래스 형식으로 만듦.

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 1.SpringWebMvc에 사용하며, 이것을 사용하기 위해 implements로 WebMvcConfigurer 인터페이스를 불러옴.
        registry.addResourceHandler(properties.getUrl() + "**")
                .addResourceLocations("file:///" + properties.getPath());
    }
}
