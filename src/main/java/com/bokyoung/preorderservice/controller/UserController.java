package com.bokyoung.preorderservice.controller;

import com.bokyoung.preorderservice.controller.request.CheckCertificationRequest;
import com.bokyoung.preorderservice.controller.request.UserJoinRequest;
import com.bokyoung.preorderservice.controller.request.UserLoginRequest;
import com.bokyoung.preorderservice.controller.response.CheckCertificationResponse;
import com.bokyoung.preorderservice.controller.response.Response;
import com.bokyoung.preorderservice.controller.response.UserJoinResponse;
import com.bokyoung.preorderservice.controller.response.UserLoginResponse;
import com.bokyoung.preorderservice.model.User;
import com.bokyoung.preorderservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //TODO : implement
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getEmail(), request.getPassword(), request.getNickname(), request.getGreeting(), request.getProfile_image(), request.getEmailVerified());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @PostMapping("/check_cerfitication")
    public Response<CheckCertificationResponse> CheckCertification(@RequestBody CheckCertificationRequest request) {
        Boolean emailVerified = userService.checkCertification(request.getEmail(), request.getCertificationNumber());
        return Response.success(new CheckCertificationResponse(emailVerified));
    }
}
