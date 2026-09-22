package org.leximatch.game.common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(
            ApiException apiException
    ){
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiException.getErrorDescription())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Api<Object>> exception(Exception exception) {

        log.error("예상하지 못한 서버 오류", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        Api.ERROR(
                                ErrorCode.SERVER_ERROR,
                                "서버 내부 오류가 발생했습니다."
                        )
                );
    }
}
