package org.koreait.member.social.controllers; // member의 서브 도메인인 social 도메인 생성, 이후 SocialController부터 처음 만듬

import lombok.RequiredArgsConstructor;
import org.koreait.member.social.entities.AuthToken;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/member/social")
@RequiredArgsConstructor
public class SocialController {

    private final RestTemplate restTemplate;

    @GetMapping("/callback")
    public void callback(@RequestParam(name="code", required = false) String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();// 요청바디에 실어 보낼 데이터
        params.add("grant_type", "authorization_code");
        params.add("client_id", "531f905a379554e2e195b0269695e6d3"); // <- 관리자쪽에 추가되어야 할 필요성이 있음
        params.add("redirect_uri", "http://localhost:3000/member/social/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params,headers); // request의 엔티티 값을 확인하기 위해

        ResponseEntity<AuthToken> response = restTemplate.postForEntity(URI.create("https://kauth.kakao.com/oauth/token"), request , AuthToken.class);

        AuthToken token = response.getBody(); // body데이터가 응답했을때 authToken의 token으로 사용함
        //System.out.println(token);

        String accessToken = token.getAccessToken();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(accessToken);

        HttpEntity<Void> request2 = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = restTemplate.exchange(URI.create("https://kapi.kakao.com/v2/user/me"), HttpMethod.GET, request2, String.class);
        System.out.println(response2);
    }
}
