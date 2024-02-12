package com.bokyoung.newsFeedService.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String greeting;

    @NotBlank
    private String profile_image;

    @NotBlank
    private Boolean emailVerified;

    public UserJoinRequest() {
    }
}
