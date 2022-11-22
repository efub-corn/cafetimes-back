package com.efub.cafetimes.util.errorutil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST : 잘못된 요청
    CANNOT_EMPTY_CONTENT(BAD_REQUEST, "내용이 비어있을 수 없습니다."),
    INVALID_VALUE(BAD_REQUEST, "올바르지 않은 값입니다."),
    INVALID_IMAGE_FILE(BAD_REQUEST, "잘못된 이미지 파일입니다."),
    INVALID_SESSION_USER(BAD_REQUEST, "세션 유저가 비어있습니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다."),


    //401 UNAUTHORIZED : 비인증 상태
    USER_UNAUTHORIZED(UNAUTHORIZED, "로그인이 필요합니다."),

    //403 FORBIDDEN : 권한 없음
    ACCESS_DENIED(FORBIDDEN, "해당 페이지를 요청할 권한이 없습니다."),

    //404 NOT_FOUND : Resource를 찾을 수 없음
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    CAFE_NOT_FOUND(NOT_FOUND, "해당 가게 정보를 찾을 수 없습니다."),
    EVENT_NOT_FOUND(NOT_FOUND, "해당 예약 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
