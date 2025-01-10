package org.koreait.koreait.member.social.services;

public interface SocialLoginService {
    String getToken(String code);
    boolean login(String token);
}
