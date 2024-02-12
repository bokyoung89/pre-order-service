package com.bokyoung.newsFeedService.controller.response;

import com.bokyoung.newsFeedService.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {

    private Long Id;
    private String email;

    public static UserResponse fromUser(UserAccount userAccount){
        return new UserResponse(
                userAccount.getId(),
                userAccount.getEmail()
        );
    }
}
