package com.bokyoung.userService.application.controller;

import com.bokyoung.userService.dto.request.UserUpdateRequest;
import com.bokyoung.userService.dto.response.Response;
import com.bokyoung.userService.dto.response.UserUpdateResponse;
import com.bokyoung.userService.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile/{id}")
    public Response<UserUpdateResponse> UpdateProfile
            (@PathVariable("id") Long id,
             @RequestBody UserUpdateRequest request) {
        userService.update(id, request.getPassword(), request.getNickName(), request.getGreeting(), request.getProfile_image());
        return Response.success(new UserUpdateResponse(id));
    }

    //TODO : implement
    @PutMapping("/password/{id}")
    public Response<Void> UpdatePassword() {
        return Response.success();
    }

    //TODO : implement
    @GetMapping("/{id}")
    public Response<Void> getProfile() {
        return Response.success();
    }

}
