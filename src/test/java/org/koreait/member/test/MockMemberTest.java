package org.koreait.member.test;

import org.junit.jupiter.api.Test;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.member.test.annotation.MockMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default", "test"}) // DB도 테스트해야하기도 하고 그런 설정을 간소화하기 위해서 넣은 어노테이션
public class MockMemberTest {

    @Autowired
    private MemberUtil memberUtil;

    @Test
    @MockMember(authority = {Authority.USER, Authority.ADMIN}) // MockMember 데이터의 권한 2개 (user,admin)을 직접 부여
    void test1() {
        Member member = memberUtil.getMember();
        System.out.println(member); // MockMember의 member 정보를 가져와서 빠진 것이 있는지 확인.
        System.out.println(memberUtil.isLogin()); // 현재유저(user012test.org)가 유저로 로그인했는가?
        System.out.println(memberUtil.isAdmin()); // 현재유저(user01@test.org)가 어드민으로 로그인했는가?

    }
}
