package org.koreait.member.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.admin.member.controllers.MemberSearch;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.paging.ListData;
import org.koreait.global.paging.Pagination;
import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Authorities;
import org.koreait.member.entities.Member;
import org.koreait.member.entities.QMember;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;
    private final JPAQueryFactory queryFactory;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));


        List<Authorities> items = member.getAuthorities();
        if (items == null) {
            Authorities auth = new Authorities();
            auth.setMember(member);
            auth.setAuthority(Authority.USER);
            items = List.of(auth);
        }


        List<SimpleGrantedAuthority> authorities = items.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority().name())).toList();

        // 추가 정보 처리
        addInfo(member);

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }

    /**
     * 회원 목록
     *
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search) {
        int page = Math.max(search.getPage(), 1); // 1보다 작을경우 1페이지를 나오도록
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit; // 0이 나오지 않게 판별식으로 고정
        int offset = (page - 1) * limit;
        QMember member = QMember.member;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        // 키워드 검색 처리 S
        String sopt = search.getSopt(); // 검색 옵션
        String skey = search.getSkey(); // 검색 키워드
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";
        /**
         * sopt - ALL : 통합 검색 - 이메일 + 회원명 + 닉네임
         *        NAME : 회원명 + 이메일
         *        Email : 이메일
         */
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            StringExpression condition;
            if (sopt.equals("EMAIL")) {
                condition = member.email;
            } else if (sopt.equals("NAME")) {
                condition = member.name.concat(member.nickName);
            } else { // 통합 검색
                condition = member.email.concat(member.name).concat(member.nickName);
            }

            andBuilder.and(condition.contains(skey));
        }
        //키워드 검색 E

        // 이메일 검색
        List<String> emails = search.getEmail();
        if (emails != null && !emails.isEmpty()) {
            andBuilder.and(member.email.in(emails));
        }

        // 권한 검색 S
        List<Authority> authorities = search.getAuthority();
        if (authorities != null && !authorities.isEmpty()) {

            //List<Authorities> _authorities = authorities.stream()
            //                .map(a -> )

           // andBuilder.and(member.authorities.)
        }
        // 권한 검색 E

        // 날짜 검색 S
        String dateType = search.getDateType();
        dateType = StringUtils.hasText(dateType) ? dateType : "createdAt"; // 가입일 기준
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        DateTimePath<LocalDateTime> condition;
        if (dateType.equals("deletedAt")) condition = member.deletedAt; // 탈퇴일 기준
        else if (dateType.equals("credentialChangedAt")) condition = member.credentialChangedAt; // 비밀번호 변경일 기준
        else condition = member.createdAt; // 가입일 기준

        if (sDate != null) {
            andBuilder.and(condition.after(sDate.atStartOfDay())); //atStartOfday는 자정으로 시간을 셋업
        }

        if (eDate != null) {
            andBuilder.and(condition.before(eDate.atTime(LocalTime.of(23,59, 59))));
        }

        // 날짜 검색 E

        /* 검색 처리 E */

        // 검색조건 만들기
        List<Member> items = queryFactory.selectFrom(member)
                .leftJoin(member.authorities)
                .fetchJoin() // fetchJoin 을 해야 leftJoin 전체를 가져옴!
                .where(andBuilder) // andBuilder 에서 정보를 가져옴!
                .orderBy(member.createdAt.desc()) // 최신순으로 내림순
                .offset(offset) // 시작 위치 설정
                .limit(limit)
                .fetch();

        long total = memberRepository.count(andBuilder); // 총 회원 수
        total = 100000L; // 임시로 넣음!
        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination); // 2차 가공하지않고 ListData로 정보 기입
//        return null; // null 값 넣어서 임시로 오류안나게 제어
    }

    /**
     * 추가 정보 처리
     * @param member
     */
    public void addInfo(Member member) {
        List<FileInfo> files = fileInfoService.getList(member.getEmail(), "profile");
        if (files != null && !files.isEmpty()) {
            member.setProfileImage(files.get(0));
        }
    }
}
