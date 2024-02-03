package com.bokyoung.preorderservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NewsFeedType {

    NEW_COMMENT_FROM_FOLLOW("%s님이 %s님의 글에 댓글을 남겼습니다."),
    NEW_LIKE_ON_POST_FROM_FOLLOW("%s님이 %s님의 글을 좋아합니다."),
    NEW_LIKE_ON_COMMENT_FROM_FOLLOW("%s님이 %s님의 댓글을 좋아합니다."),
    NEW_FOLLOW_FROM_FOLLOW("%s님이 %s님을 팔로우합니다."),
    NEW_COMMENT_ON_POST("%s님이 %s 포스트에 댓글을 남겼습니다."),
    NEW_LIKE_ON_POST("%s님이 %s 포스트를 좋아합니다."),
    NEW_LIKE_ON_COMMENT("%s님이 %s님의 댓글을 좋아합니다."),
    NEW_FOLLOW("%s님이 %s님을 팔로우합니다.")
    ;

    private final String newsFeedText;

}