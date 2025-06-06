package com.sbs.tutorial1.boundedContext.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class Rq
{
    private final HttpServletRequest req;
    private final HttpServletResponse resp;


    public void setCoolie(String name, long value)
    {
        setCoolie(name, value + "");
    }

    private void setCoolie(String name, String value)
    {
        resp.addCookie(new Cookie(name, value));
//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24 * 365); // 1년
//        resp.addCookie(cookie);
    }

    public boolean removeCoolie(String name)
    {
        if (req.getCookies() != null) //요청에 쿠키가 있으면
        {
            System.out.println("before removing cookie --> " + Arrays.toString(req.getCookies()));
            Arrays.stream(req.getCookies()) //요청에 있는 쿠키들을 스트림으로 변환
                .filter(c -> c.getName().equals("loggedInMemberId")) //쿠키 이름이 count 인 것만 필터링
                //.forEach(cookie -> cookie.setMaxAge(0)); //쿠키만료
                //.forEach(cookie -> {cookie.setMaxAge(0); resp.addCookie(cookie);}); //쿠키만료
                .forEach(cookie -> {
                    cookie.setMaxAge(0); //쿠키만료
                    resp.addCookie(cookie); //민료된 쿠키 다시 추가
                });
            System.out.println("after removing cookie --> " + Arrays.toString(req.getCookies()));
            return Arrays.stream(req.getCookies())
                    .anyMatch(c -> c.getName().equals(name)); //anyMatch 는 스트림에서 하나라도 조건에 맞는 요소가 있으면 true 를 반환한다.
        }
        return false; //쿠키가 없으면 false 반환
    }

    public long getCoolieAsLong(String name, long defaultValue)
    {
        String value = getCookie(name, null);

        if (value == null) return defaultValue; //쿠키가 없으면 0을 반환

        try {
            return Long.parseLong(value); //문자열을 정수로 변환
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getCookie(String name, String defaultValue)
    {
        if (req.getCookies() == null) //요청에 쿠키가 없으면
            return defaultValue;
        return Arrays.stream(req.getCookies()) //요청에 있는 쿠키들을 스트림으로 변환
            .filter(c -> c.getName().equals(name)) //쿠키 이름이 count 인 것만 필터링
            .map(Cookie::getValue) //필터링된 쿠키의 값을 가져온다.
            .findFirst() //필터링된 것 중에 첫번째를 찾는다.
            .orElse(defaultValue); //없으면 0을 반환
    }

}
