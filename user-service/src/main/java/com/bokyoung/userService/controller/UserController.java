package com.bokyoung.userService.controller;

import com.bokyoung.userService.controller.request.UserUpdateRequest;
import com.bokyoung.userService.controller.response.Response;
import com.bokyoung.userService.controller.response.UserUpdateResponse;
import com.bokyoung.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
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
