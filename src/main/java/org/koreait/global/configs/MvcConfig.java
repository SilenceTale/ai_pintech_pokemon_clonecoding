package org.koreait.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 자동스캔범위에 포함되는 범위일때 Configurer을 넣는다.
@EnableJpaAuditing // 엔티티의 변화를 자동 감지하기 위해서 사용한다, 서버의 부담을 줄여주기 위해.
@EnableScheduling // 이벤트 방식의 일종, @Scheduled 설정을 사용하기 위해서 사용한다.
@EnableRedisHttpSession // 세션 키값을 동일하게 만들어주고 분리함으로써 사용하기 용이함. (ex 구글에 로그인 - 이메일이나 유튜브에도 자동 로그인되는 것 처럼 다른 서버여도 공통적인 키값을 전달함으로써 사용하기 편하게.)
//Redis를 설정함으로써 RedisConfig의 설정값을 지정할 수도 있음.
public class MvcConfig implements WebMvcConfigurer { // 뭐가 필요한지 모른다면 WebMvcConfigurer를 확인해서 필요한게 뭔지 확인하자!
    /**
     * 정적 경로 설정, CSS, JS, 이미지
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // WebMvcConfigurer에 들어있는 메서드.
        registry.addResourceHandler("/**")
                // /**는 파일이름을 포함한 모든 파일을 의미한다. ?는 문자 한개를 의미한다.
                .addResourceLocations("classpath:/static/");
        // classpath : 클래스 파일을 인식할 수 있는 경로 주로 정적인 경로를 넣는다.
        // 그것은 resourse 파의 static파일의 경로를 의미한다.
    }

    /**
     * PATCH, PUT, DELETE 등등
     * PATCH 메서드로 요청을 보내는 경우
     * <form method='POST' ...>
     *      <input type='hidden' name='_method' value='PATCH'>
     * </form>
     * @return
     */
    // 히든 메서드 안에 위에 주석을 넣어 교체하는 식일때 진행을 할 수있다. 주로 관리자일때 숨기고 싶을때 사용한다!
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
