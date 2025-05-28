package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.boundedContext.base.rsData.ResultData;
import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

//25 02 04, 스프링부트 기초, 20강, 로그인 정보가 올바른지 체크, user1/1234 로 로그인 했을 때만 성공처리, RsData 클래스 도입하여 공통 보고서 양식을 도입
@Controller //new MemberController() 가 없지만 @Controller --> F3 --> See @Component --> 객체가 자동으로 만들어 진다.
@RequestMapping("/member") //이렇게 하면 MemberController 에서 /member로 시작하는 URL을 처리할 수 있습니다.
@AllArgsConstructor
public class MemberController
{

    /*
    @Autowired  //필드 주입 방식--> NullPointException 이 가끔 발생하므로 생정자 주입 방식을 주로 사용한다.
    private MemberService memberService;
    */

    private final MemberService memberService;
//    //생성자 주입방식 --> 대신에 롬복을 써도 된다. 25 02 06, 스프링부트 기초, 24강, MemberRepository 클래스를 Ioc 컨테이너에 등록한 후, 필요한 곳에서 @Autowire 를 사용하여 리모콘을 공유받음
//    public MemberController(MemberService memberService)
//    {
//        this.memberService = memberService;
//    }
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
    //25 02 06, 스프링부트 기초, 26강, 쿠키를 이용해서 로그인 상태 구현
    @GetMapping("/me") //@RequestMapping("/member") 했기 때문에 /member/login 과 동일하다.
    /*
    http://localhost:8080/member/me
    https://youtu.be/6FfpdXjqW_w?list=PLmAWMAo-opQxBRwmZjoFzTynYyB-TqfoM&t=1095
    localhost:8080/member/login?username=user1&password=1234
    http://localhost:8080/member/me
    {
      "resultCode": "S-1",
      "msg": "당신의 username은 user1 입니다.",
      "data": null,
      "success": true
    }
    see Cookie
    http://localhost:8080/member/logout
    {
      "resultCode": "S-1",
      "msg": "로그아웃 되었습니다.",
      "data": null,
      "success": true
    }
    see no Cookie
    http://localhost:8080/member/me
    {
      "resultCode": "F-1",
      "msg": "로그인 후에 이용해 주세요.",
      "data": null,
      "success": false
    }
    */
    @ResponseBody
    public ResultData showMe(HttpServletRequest req, HttpServletResponse resp)
    {
        long loggedInMemberId = 0;

        if (req.getCookies() != null) //요청에 쿠키가 없으면
        {
            loggedInMemberId = Arrays.stream(req.getCookies()) //요청에 있는 쿠키들을 스트림으로 변환
                .filter(c -> c.getName().equals("loggedInMemberId")) //쿠키 이름이 count 인 것만 필터링
                .map(Cookie::getValue) //필터링된 쿠키의 값을 가져온다.
                //.mapToInt(Integer::parseInt) //문자열을 정수로 변환
                .mapToLong(Long::parseLong)
                .findFirst() //필터링된 것 중에 첫번째를 찾는다.
                .orElse(0); //없으면 0을 반환
        }

        boolean isLoggedIn = loggedInMemberId > 0;

        if (!isLoggedIn)
            return ResultData.of("F-1", "로그인 후에 이용해 주세요.");

        Member member = memberService.findById(loggedInMemberId);

        return ResultData.of("S-1", "당신의 username은 %s 입니다.".formatted(member.getUsername()));
    }

    //localhost:8080/member/login?username=user1&password=1234
    @GetMapping("/login") //@RequestMapping("/member") 했기 때문에 /member/login 과 동일하다.
    @ResponseBody
    public ResultData login(String username, String password, HttpServletResponse resp) {
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

        ResultData resultData = memberService.tryLogin(username, password);
        if (resultData.isSuccess())
        {
            long memerId = (long) resultData.getData();
            resp.addCookie(new Cookie("loggedInMemberId", memerId + ""));
            //https://youtu.be/6FfpdXjqW_w?list=PLmAWMAo-opQxBRwmZjoFzTynYyB-TqfoM&t=614
        }
        return memberService.tryLogin(username, password);
    }

    @GetMapping("/logout") //@RequestMapping("/member") 했기 때문에 /member/login 과 동일하다.
    //http://localhost:8080/member/logout --> 브라우저 F10 --> 쿠키가 삭제 됨
    @ResponseBody
    public ResultData logout(HttpServletRequest req, HttpServletResponse resp)
    {
        if (req.getCookies() != null) //요청에 쿠키가 없으면
        {
            Arrays.stream(req.getCookies()) //요청에 있는 쿠키들을 스트림으로 변환
                .filter(c -> c.getName().equals("loggedInMemberId")) //쿠키 이름이 count 인 것만 필터링
                //.forEach(cookie -> cookie.setMaxAge(0)); //쿠키만료
                //.forEach(cookie -> {cookie.setMaxAge(0); resp.addCookie(cookie);}); //쿠키만료
                .forEach(cookie -> {
                    cookie.setMaxAge(0); //쿠키만료
                    resp.addCookie(cookie); //민료된 쿠키 다시 추가
                });
        }
        return ResultData.of("S-1", "로그아웃 되었습니다.");
    }
}
