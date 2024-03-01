package com.bokyoung.activityService.common.infra.userAccount;

import com.bokyoung.activityService.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserAccountFeignClient {

    @GetMapping("/api/internal/userAccount/principal")
    Response<UserResponse> getUserAccountByPrincipalId(@RequestHeader(name = "principalId") Long principalId);

    @GetMapping("/api/internal/userAccount/user")
    Response<UserResponse> getUserAccountByUserId(@RequestParam(name = "userId") Long userId);
}
