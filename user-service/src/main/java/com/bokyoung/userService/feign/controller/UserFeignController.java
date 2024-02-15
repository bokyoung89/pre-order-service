package com.bokyoung.userService.feign.controller;

import com.bokyoung.userService.controller.response.Response;
import com.bokyoung.userService.controller.response.UserResponse;
import com.bokyoung.userService.feign.service.UserFeignService;
import com.bokyoung.userService.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal")
public class UserFeignController {

    private final UserFeignService userFeignService;

    @GetMapping("/userAccount/principal")
    public Response<UserResponse> getUserAccountByPrincipalId(@RequestHeader(name = "principalId") Long principalId) {
        UserAccount userAccount = userFeignService.getUserAccount(principalId);
        return Response.success(UserResponse.fromUser(userAccount));
    }

    @GetMapping("/userAccount/user")
    public Response<UserResponse> getUserAccountByUserId(@RequestParam(name = "userId") Long userId) {
        UserAccount userAccount = userFeignService.getUserAccount(userId);
        return Response.success(UserResponse.fromUser(userAccount));
    }
}
