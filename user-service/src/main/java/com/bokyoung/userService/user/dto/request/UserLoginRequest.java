package com.bokyoung.userService.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public UserLoginRequest() {
    }
}
