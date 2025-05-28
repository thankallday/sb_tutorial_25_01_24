package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.boundedContext.base.rsData.ResultData;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//25 02 04, 스프링부트 기초, 20강, 로그인 정보가 올바른지 체크, user1/1234 로 로그인 했을 때만 성공처리, RsData 클래스 도입하여 공통 보고서 양식을 도입
@Controller //new MemberController() 가 없지만 @Controller --> F3 --> See @Component --> 객체가 자동으로 만들어 진다.
@RequestMapping("/member") //이렇게 하면 MemberController 에서 /member로 시작하는 URL을 처리할 수 있습니다.
public class MemberController
{

    /*
    @Autowired  //필드 주입 방식--> NullPointException 이 가끔 발생하므로 생정자 주입 방식을 주로 사용한다.
    private MemberService memberService;
    */

    private final MemberService memberService;
    //생성자 주입방식
    public MemberController(MemberService memberService)
    {
        this.memberService = memberService;
    }
//    private MemberController ()  ----->>> @Autowired 로 대체된다. -->> IOC 컨테이너에 의해 자동으로 memberService 객체가 만들어진다.
//    {
//        memberService = new MemberService();
//    }

//    //localhost:8080/member/login?username=user1&password=1234
//    @GetMapping("/login") //@RequestMapping("/member") 했기 때문에 /member/login 과 동일하다.
//    @ResponseBody
//    public Map<String, Object> login(String username, String password) {
//        Map<String, Object> rsData = new LinkedHashMap<>() {{
//            put("resultCode", "S-1");
//            put("msg", "%s 님 환영합니다.".formatted(username));
//        }};
//        return rsData;
//    }
    //localhost:8080/member/login?username=user1&password=1234
    @GetMapping("/login") //@RequestMapping("/member") 했기 때문에 /member/login 과 동일하다.
    @ResponseBody
    public ResultData login(String username, String password) {
//        Map<String, Object> rsData = new LinkedHashMap<>() {{
//            put("resultCode", "S-1");
//            put("msg", "%s 님 환영합니다.".formatted(username));
//        }};
        if(username == null || username.trim().isEmpty())
        {
            return ResultData.of("F-1", "로그인 아이디를 입력해 주세요.");
        }
        if(password == null || password.trim().isEmpty())
        {
            return ResultData.of("F-2", "로그인 비번을 입력해 주세요.");
        }
//        memberService.tryLogin(username, password);
//        return ResultData.of("S-1", "%s 님 환영합니다.".formatted(username));

        return memberService.tryLogin(username, password);
    }
}
