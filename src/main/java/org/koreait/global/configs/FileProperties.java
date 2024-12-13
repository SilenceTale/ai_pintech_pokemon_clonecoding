package org.koreait.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="file.upload")
public class FileProperties {
    private String path;
    private String url;
}
// 범주화해서 설정파일을 사용하기 위해서 만든 클래스.