package com.bokyoung.activityService.common.infra.newsFeed;

import com.bokyoung.activityService.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "newsFeed-service", url="${feign.url.prefix}")
public interface NewsFeedFeignClient {

    @PostMapping("/api/internal/newsFeed")
    Response<Void> createNewsFeed(NewsFeedCreateRequest request);

}
