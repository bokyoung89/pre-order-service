package com.bokyoung.userService.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @NotBlank
    private String greeting;

    @NotBlank
    private String profile_image;

    public UserUpdateRequest() {
    }

}
