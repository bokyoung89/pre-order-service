package com.bokyoung.userService.dto.response;

import com.bokyoung.userService.domain.model.UserAccount;
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
