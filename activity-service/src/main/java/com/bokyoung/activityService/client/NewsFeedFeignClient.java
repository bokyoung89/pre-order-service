package com.bokyoung.activityService.client;

import com.bokyoung.activityService.controller.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "newsFeed-service", url="${feign.url.prefix}")
public interface NewsFeedFeignClient {

    @PostMapping("/api/internal/newsFeed")
    Response<Void> createNewsFeed(NewsFeedCreateRequest request);

}
