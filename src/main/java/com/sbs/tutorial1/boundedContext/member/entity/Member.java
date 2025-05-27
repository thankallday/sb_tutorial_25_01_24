package com.sbs.tutorial1.boundedContext.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//25 02 04, 스프링부트 기초, 21강, Member, MemberRepository 도입, 회원 10명 추가
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member
{
    private static long lastId;
    private final long id;
    private String username;
    private String password;

    static {
        lastId = 0;
    }

    //ALT+Insert -->> select "Constructor" -->> select "username", "password"
    public Member(String username, String password)
    {
//        this.username = username;
//        this.password = password;
        this(++lastId, username, password);
    }
}
