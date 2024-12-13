package org.koreait.global.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;
 // BeansConfig은 자주 사용하는 것을 수동 관리를 하기 위함이다.
@Configuration
public class BeansConfig {

    @Lazy
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    } // 원격에 요청을 받을때 지정해주는 템플릿. 주로 반환값이 2가지이다.

    @Lazy
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper;
    } // 자동으로 get, set 패턴을 알아서 체크해주는, 범용적인 기능에 자동으로 추가해준다. 클래스클래스가 필수로 들어가며 공용기능을 사용할떄는 무족건 class클래스 객체가 들어간다 주로 범용적인 기능에 사용됀다.


     //RestController를 보면 ObjectMapper로 구현돼어있다.
     //JSONdata를 자바 데이터로 변환해줄때도 사용된다.
    @Lazy
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule()); // java8 data & time api - java.time 패키지

        return om;
    }
}
