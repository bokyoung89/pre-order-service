package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CheckCertificationResponse {

    private Boolean emailVerified;

    public static CheckCertificationResponse fromUser(User user){
        return new CheckCertificationResponse(
                user.isEmailVerified()
        );
    }
}
