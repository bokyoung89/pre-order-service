package com.bokyoung.preorderservice.fixture;

import com.bokyoung.preorderservice.domain.UserAccount;

public class UserAccountFixture {

    public static UserAccount get(String email, String password) {
        UserAccount result = new UserAccount();
        result.setId(1L);
        result.setEmail(email);
        result.setPassword(password);
        return result;
    }
}
