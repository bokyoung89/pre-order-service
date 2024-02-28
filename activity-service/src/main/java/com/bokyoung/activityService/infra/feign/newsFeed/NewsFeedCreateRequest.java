package com.bokyoung.activityService.infra.feign.newsFeed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewsFeedCreateRequest {

    private Long userId;

    private NewsFeedType newsFeedType;

    private NewsFeedArgs newsFeedArgs;

    public NewsFeedCreateRequest() {
    }
}
