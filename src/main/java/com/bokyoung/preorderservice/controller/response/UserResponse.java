package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class UserResponse {

    private Long Id;
    private String email;

    public static UserResponse fromUser(UserResponse userResponse){
        return new UserResponse(
                userResponse.getId(),
                userResponse.getEmail()
        );
    }
}
