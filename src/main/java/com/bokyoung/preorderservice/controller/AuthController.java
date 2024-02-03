package com.bokyoung.preorderservice.controller;

import com.bokyoung.preorderservice.controller.request.CheckCertificationRequest;
import com.bokyoung.preorderservice.controller.request.UserJoinRequest;
import com.bokyoung.preorderservice.controller.request.UserLoginRequest;
import com.bokyoung.preorderservice.controller.response.CheckCertificationResponse;
import com.bokyoung.preorderservice.controller.response.Response;
import com.bokyoung.preorderservice.controller.response.UserJoinResponse;
import com.bokyoung.preorderservice.controller.response.UserLoginResponse;
import com.bokyoung.preorderservice.model.UserAccount;
import com.bokyoung.preorderservice.service.AuthService;
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
