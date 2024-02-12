package com.bokyoung.newsFeedService.controller.response;

import com.bokyoung.newsFeedService.model.UserAccount;
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