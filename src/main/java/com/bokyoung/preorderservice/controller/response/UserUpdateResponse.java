package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateResponse {

    private Long id;

    public static UserUpdateResponse fromUser(UserAccount userAccount){
        return new UserUpdateResponse(
                userAccount.getId()
        );
    }
}