package com.bokyoung.activityService.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAccountResponse {

    private Long Id;
    private String nickName;

    public static UserAccountResponse fromUser(UserAccountResponse userAccount){
        return new UserAccountResponse(
                userAccount.getId(),
                userAccount.getNickName()
        );
    }
}
