package com.bokyoung.preorderservice.fixture;

import com.bokyoung.activityService.model.entity.UserAccountEntity;

public class UserAccountFixture {

    public static UserAccountEntity get(String email, String password) {
        UserAccountEntity result = new UserAccountEntity();
        result.setId(1L);
        result.setEmail(email);
        result.setPassword(password);
        return result;
    }
}
