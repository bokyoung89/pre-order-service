package com.bokyoung.newsFeedService.controller;

import com.bokyoung.newsFeedService.controller.response.CheckCertificationResponse;
import com.bokyoung.newsFeedService.controller.request.CheckCertificationRequest;
import com.bokyoung.newsFeedService.controller.request.UserJoinRequest;
import com.bokyoung.newsFeedService.controller.request.UserLoginRequest;
import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.controller.response.UserJoinResponse;
import com.bokyoung.newsFeedService.controller.response.UserLoginResponse;
import com.bokyoung.newsFeedService.model.UserAccount;
import com.bokyoung.newsFeedService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
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
