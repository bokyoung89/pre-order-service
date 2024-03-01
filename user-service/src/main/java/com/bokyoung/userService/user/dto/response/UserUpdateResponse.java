package com.bokyoung.userService.user.dto.response;

import com.bokyoung.userService.user.domain.model.UserAccount;
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