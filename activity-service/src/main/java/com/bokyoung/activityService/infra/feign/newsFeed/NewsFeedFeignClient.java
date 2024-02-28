package com.bokyoung.activityService.infra.feign.newsFeed;

import com.bokyoung.activityService.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "newsFeed-service", url="${feign.url.prefix}")
public interface NewsFeedFeignClient {

    @PostMapping("/api/internal/newsFeed")
    Response<Void> createNewsFeed(NewsFeedCreateRequest request);

}
