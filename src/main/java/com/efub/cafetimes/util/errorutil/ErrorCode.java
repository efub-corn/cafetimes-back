package com.efub.cafetimes.util.errorutil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    //404 NOT_FOUND : Resource를 찾을 수 없음
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    SUBSCRIPTION_NOT_FOUND(NOT_FOUND, "해당 구독 정보를 찾을 수 없습니다"),

    //400 BAD_REQUEST
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
