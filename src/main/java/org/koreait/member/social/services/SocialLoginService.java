package org.koreait.member.social.services;

public interface SocialLoginService {
    String getToken(String code);
    boolean login(String token); // id값이 token이 될 예정이다.
}
