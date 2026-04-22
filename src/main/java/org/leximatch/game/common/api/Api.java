package org.leximatch.game.common.api;

import jakarta.validation.Valid;
import lombok.*;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.error.ErrorCodeIfs;

// Todo : Null 체크 및 Valid 추가 필요
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;
    @Valid
    private T body;

    public static <T> Api<T> OK(T data){
        var api =new Api<T>();
        api.result= Result.OK();
        api.body =data;
        return api;
    }

    public static Api<Object> ERROR(Result result){
        var api =new Api<Object>();
        api.result= result;

        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        var api =new Api<Object>();
        api.result= Result.ERROR(errorCodeIfs);

        return api;
    }


    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, String description){
        var api =new Api<Object>();
        api.result= Result.ERROR(errorCodeIfs, description);

        return api;
    }
}

