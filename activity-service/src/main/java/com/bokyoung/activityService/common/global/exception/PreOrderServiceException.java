package com.bokyoung.activityService.common.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO : implement
@Getter
@AllArgsConstructor
public class PreOrderServiceException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public PreOrderServiceException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = null;

    }

    @Override
    public String getMessage() {
        if(message == null){
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
