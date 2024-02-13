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

    @GetMapping("/userAccount")
    public Response<UserResponse> getUserAccount
            (@RequestHeader(name = "principalEmail") String principalEmail) {
        UserAccount userAccount = userFeignService.getUserAccount(principalEmail);
        return Response.success(UserResponse.fromUser(userAccount));
    }
}
