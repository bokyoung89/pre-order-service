package com.bokyoung.userService.dto.response;

import com.bokyoung.userService.domain.model.UserAccount;
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
