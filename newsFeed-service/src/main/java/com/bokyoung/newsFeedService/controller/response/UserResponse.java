package com.bokyoung.newsFeedService.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {

    private Long Id;
    private String email;

    private String nickname;

    public static UserResponse fromUser(UserResponse userAccount){
        return new UserResponse(
                userAccount.getId(),
                userAccount.getEmail(),
                userAccount.getNickname()
        );
    }
}
