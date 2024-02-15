package com.bokyoung.newsFeedService.controller.request;

import com.bokyoung.newsFeedService.model.NewsFeedArgs;
import com.bokyoung.newsFeedService.model.NewsFeedType;
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
