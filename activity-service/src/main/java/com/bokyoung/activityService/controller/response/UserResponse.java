package com.bokyoung.activityService.controller.response;

import com.bokyoung.activityService.model.UserAccount;
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
