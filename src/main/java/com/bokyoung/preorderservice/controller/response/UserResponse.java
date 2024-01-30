package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.UserAccount;
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
