package com.bokyoung.preorderservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AlarmType {
    NEW_COMMENT_ON_POST("new comment!"),
    NEW_LIKE_ON_POST("new liked post!"),
    NEW_LIKE_ON_COMMENT("new liked comment!")
    ;

    private final String alarmText;
}