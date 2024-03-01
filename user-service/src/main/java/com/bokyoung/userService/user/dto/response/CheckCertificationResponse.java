package com.bokyoung.userService.user.dto.response;

import com.bokyoung.userService.user.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckCertificationResponse {

    private Boolean emailVerified;

    public static CheckCertificationResponse fromUser(UserAccount userAccount) {
        return new CheckCertificationResponse(
                userAccount.isEmailVerified()
        );
    }
}
