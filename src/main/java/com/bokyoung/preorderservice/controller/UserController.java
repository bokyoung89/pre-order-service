package com.bokyoung.preorderservice.controller;

import com.bokyoung.preorderservice.controller.request.UserUpdateRequest;
import com.bokyoung.preorderservice.controller.response.AlarmResponse;
import com.bokyoung.preorderservice.controller.response.Response;
import com.bokyoung.preorderservice.controller.response.UserUpdateResponse;
import com.bokyoung.preorderservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public Response<UserUpdateResponse> updateUser
            (@PathVariable("id") Long id,
             @RequestBody UserUpdateRequest request) {
        userService.update(id, request.getPassword(), request.getNickName(), request.getGreeting(), request.getProfile_image());
        return Response.success(new UserUpdateResponse(id));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        return Response.success(userService.alarmList(authentication.getName(), pageable).map(AlarmResponse::fromAlarm));
    }

}
