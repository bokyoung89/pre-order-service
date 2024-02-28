package com.bokyoung.userService.user.dto.response;

import com.bokyoung.userService.user.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private Long id;
    private String email;

    public static UserJoinResponse fromUser(UserAccount userAccount){
        return new UserJoinResponse(
                userAccount.getId(),
                userAccount.getEmail()
        );
    }
}
