package com.bokyoung.activityService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
    ALREADY_LIKED_POST(HttpStatus.CONFLICT, "User already liked post"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not founded"),
    ALREADY_LIKED_COMMENT(HttpStatus.CONFLICT, "User already liked comment"),
    ALREADY_FOLLOW(HttpStatus.CONFLICT, "this user already following"),
    SELF_FOLLOWING(HttpStatus.BAD_REQUEST, "Cannot follow yourself")
    ;

    private HttpStatus status;
    private String message;
}
