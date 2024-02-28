package com.bokyoung.newsFeedService.newsFeed.dto.request;

import com.bokyoung.newsFeedService.newsFeed.domain.model.NewsFeedArgs;
import com.bokyoung.newsFeedService.newsFeed.domain.model.NewsFeedType;
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
