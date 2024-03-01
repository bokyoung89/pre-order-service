package com.bokyoung.userService.user.dto.response;

import com.bokyoung.userService.user.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {

    private Long Id;
    private String email;
    private String nickname;

    public static UserResponse fromUser(UserAccount userAccount){
        return new UserResponse(
                userAccount.getId(),
                userAccount.getEmail(),
                userAccount.getNickname()
        );
    }
}
