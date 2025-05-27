package com.sbs.tutorial1.boundedContext.member.service;


import com.sbs.tutorial1.boundedContext.base.rsData.ResultData;
import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

//25 02 04, 스프링부트 기초, 20강, 로그인 정보가 올바른지 체크, user1/1234 로 로그인 했을 때만 성공처리, RsData 클래스 도입하여 공통 보고서 양식을 도입
@Service //스프링부트가 해당 클래스를 서비스로 인식한다
public class MemberService
{
    //25 02 04, 스프링부트 기초, 21강, Member, MemberRepository 도입, 회원 10명 추가
    private MemberRepository memberRepository;

    public MemberService()
    {
        memberRepository = new MemberRepository();
    }


    //localhost:8080/member/login?username=user1&password=1234
    public ResultData tryLogin(String username, String password)
    {
//        if(!username.equals("user1"))
//            return ResultData.of("F-3", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
//        if(!password.equals("1234"))
//            return ResultData.of("F-4", "비밀번호가 일치하지 않습니다.");
//
//        return ResultData.of("S-1", "%s 님 환영합니다.".formatted(username));

        Member member = memberRepository.findByUsername(username);

        if (member == null)
            return ResultData.of("F-3", "%s(은)는 존재하지 않는 회원입니다.".formatted(member.getUsername()));
        if(!member.getPassword().equals(password))
            return ResultData.of("F-4", "비밀번호가 일치하지 않습니다.");
        return ResultData.of("S-1", "%s 님 환영합니다.".formatted(username));
    }
}
