package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.UserAccount;
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
