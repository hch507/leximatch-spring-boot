package org.leximatch.game.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//200 성공

//4000번대 요청/검증 오류
//4010번대 인증 오류
//4030번대 권한 오류
//4040번대 데이터 없음

//5000번대 서버 내부 오류
//5020번대 외부 서버/FastAPI 오류

//6000번대 게임 도메인 오류
@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{

    OK(HttpStatus.OK.value(), 200, "SUCCESS"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 4000, "BAD_REQUEST"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), 4001, "INVALID_INPUT"),

    NOT_FOUND(HttpStatus.NOT_FOUND.value(), 4040, "NOT_FOUND"),
    DAILY_WORD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 4041, "DAILY_WORD_NOT_FOUND"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000, "INTERNAL_SERVER_ERROR"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5002, "NULL_POINT"),

    FAST_API_ERROR(HttpStatus.BAD_GATEWAY.value(), 5020, "FAST_API_ERROR"),
    FAST_API_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT.value(), 5021, "FAST_API_TIMEOUT"),
    FAST_API_INVALID_RESPONSE(HttpStatus.BAD_GATEWAY.value(), 5022, "FAST_API_INVALID_RESPONSE");


    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;
}
