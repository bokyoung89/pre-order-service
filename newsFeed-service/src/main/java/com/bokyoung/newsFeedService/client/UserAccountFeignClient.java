package com.bokyoung.newsFeedService.client;

import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.controller.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${feign.url.prefix}")
public interface UserAccountFeignClient {

    @GetMapping("/api/internal/userAccount")
    Response<UserResponse> getUserAccount(@RequestHeader(name = "principalId") Long principalId);
}
