package com.sbs.tutorial1.boundedContext.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

//25 02 04, 스프링부트 기초, 20강, 로그인 정보가 올바른지 체크, user1/1234 로 로그인 했을 때만 성공처리, RsData 클래스 도입하여 공통 보고서 양식을 도입
@AllArgsConstructor
@Getter
public class ResultData
{
    private final String resultCode; //결과 코드
    private final String msg;
    private final Object data;

    public static ResultData of(String resultCode, String msg)
    {
        return new ResultData(resultCode, msg, null);
    }

    public static ResultData of(String resultCode, String msg, Object data)
    {
        return new ResultData(resultCode, msg, data);
    }

    public boolean isSuccess()
    {
        return resultCode.startsWith("S-");
    }
}
