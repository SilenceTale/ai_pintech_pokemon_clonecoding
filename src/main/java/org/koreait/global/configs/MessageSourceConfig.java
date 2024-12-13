package org.koreait.global.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig { // 메세지 코드에 관한 부분. 쉽게 관리하기 위해서 따로 나눔.

    @Bean
    public MessageSource messageSource() { // 메세지 소스 빈을 등록 => messageSource를 Bean으로 등록, 수동으로도 관리할 수 있도록 지정함.
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.addBasenames("messages.commons", "messages.validations", "messages.errors", "messages.pokemon");
        // 공통적인 메세지, 검증, 에러, 기능을 사용하기 위해 따로 빼놓은 것.
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(true);
        //현재 메세지가 없어도 코드 형식으로 넣어주기 위해서.

        return ms;
    }
}
