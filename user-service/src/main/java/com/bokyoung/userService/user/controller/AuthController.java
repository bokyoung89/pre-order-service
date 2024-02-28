package com.bokyoung.userService.user.controller;

import com.bokyoung.userService.user.dto.request.CheckCertificationRequest;
import com.bokyoung.userService.user.dto.request.UserJoinRequest;
import com.bokyoung.userService.user.dto.request.UserLoginRequest;
import com.bokyoung.userService.user.dto.response.CheckCertificationResponse;
import com.bokyoung.userService.user.dto.response.Response;
import com.bokyoung.userService.user.dto.response.UserJoinResponse;
import com.bokyoung.userService.user.dto.response.UserLoginResponse;
import com.bokyoung.userService.user.domain.model.UserAccount;
import com.bokyoung.userService.user.application.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //TODO : implement
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserAccount userAccount = authService.join(request.getEmail(), request.getPassword(), request.getNickname(), request.getGreeting(), request.getProfile_image(), request.getEmailVerified());
        return Response.success(UserJoinResponse.fromUser(userAccount));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @PostMapping("/checkCerfitication")
    public Response<CheckCertificationResponse> checkCertification(@RequestBody CheckCertificationRequest request) {
        Boolean emailVerified = authService.checkCertification(request.getEmail(), request.getCertificationNumber());
        return Response.success(new CheckCertificationResponse(emailVerified));
    }
}
