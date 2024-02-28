package com.bokyoung.newsFeedService.dto.request;

import com.bokyoung.newsFeedService.domain.model.NewsFeedArgs;
import com.bokyoung.newsFeedService.domain.model.NewsFeedType;
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
