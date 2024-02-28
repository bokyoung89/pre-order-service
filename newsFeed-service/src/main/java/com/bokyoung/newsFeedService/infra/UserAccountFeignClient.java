package com.bokyoung.newsFeedService.infra;

import com.bokyoung.newsFeedService.newsFeed.dto.response.Response;
import com.bokyoung.newsFeedService.newsFeed.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${feign.url.prefix}")
public interface UserAccountFeignClient {

    @GetMapping("/api/internal/userAccount/principal")
    Response<UserResponse> getUserAccountByPrincipalId(@RequestHeader(name = "principalId") Long principalId);
}
