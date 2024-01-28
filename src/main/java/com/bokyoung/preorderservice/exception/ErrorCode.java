package com.bokyoung.preorderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "User email is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is invalid"),

    INVALID_INPUT_PASSWORD(HttpStatus.BAD_REQUEST, "invalid input password"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),

    INVALID_EMAIL_TOKEN(HttpStatus.BAD_REQUEST, "mail is invalid"),

    DUPLICATE_EMAIL_CHECK_TOKEN(HttpStatus.CONFLICT, "email check token already exists"),
    INVALID_CERTIFICATION(HttpStatus.UNAUTHORIZED, "certification number is invaild")
    ;

    private HttpStatus status;
    private String message;
}
