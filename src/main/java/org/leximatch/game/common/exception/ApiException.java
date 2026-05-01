package org.leximatch.game.common.exception;

import lombok.Getter;
import org.leximatch.game.common.error.ErrorCodeIfs;

@Getter
public class ApiException extends RuntimeException{

    private final ErrorCodeIfs errorCodeIfs;

    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs){
        super(errorCodeIfs.getDescription());

        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        super(errorDescription);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

}
