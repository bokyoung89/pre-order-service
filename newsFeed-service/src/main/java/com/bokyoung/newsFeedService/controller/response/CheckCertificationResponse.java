package com.bokyoung.newsFeedService.controller.response;

import com.bokyoung.newsFeedService.model.UserAccount;
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
