package com.sbs.tutorial1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//개발자가 스프링부트에게
//이 클래스는 웹 요청을 받아서 작업을 한다
//이 클래스는 Controller 야!!!
public class HomeController
{
    @GetMapping("/home/main")
    //개발자가 /home/main 이르는 요청을 보내면  아래 메소드를 실행하라.
    //localhost:8080/home/main
    @ResponseBody
    //아래 메소드를 실행해서 body에 출력해라.
    public String showHome()
    {
        return "어서오세요";
    }

    @GetMapping("/home/main2")
    //localhost:8080/home/main2
    @ResponseBody
    public String showHome2()
    {
        return "환영합니다.";
    }

    @GetMapping("/home/main3")
    //localhost:8080/home/main3
    @ResponseBody
    public String showHome3()
    {
        return "스프링부트 획기적이다.";
    }

    @GetMapping("/home/plus")
    //localhost:8080/home/plus?a=1&b=1 --> 2
    //try localhost:8080/home/plus?a=1&bbb=1 --> Error
    //URL 요청시 넘겨준 파라미터 값이 매개변수에 들어간다.
    @ResponseBody
    //public int showPlus(int a, int b) //URL parameter 이름과 매개변수 이름이 일치해야 한다. (스프링부트 규칙)
    //try localhost:8080/home/plus?a=1 --> Error
    //public int showPlus(int a, @RequestParam int b) //localhost:8080/home/plus?a=1 --> Error
    //@RequestParam 생략 가능
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b)
    //localhost:8080/home/plus?a=1 --> good
    //localhost:8080/home/plus?aaa=1 --> 0 --> bad
    //localhost:8080/home/plus?aaa=1&bbb=1 --> 0 --> bad
    {
        return a + b;
    }
}
