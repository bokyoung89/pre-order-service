package com.bokyoung.preorderservice.model;

import com.bokyoung.preorderservice.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {

    private Long Id;
    private String email;
    private String password;
    private String nickname;
    private String greeting;
    private String profile_image;
    private boolean emailVerified;
    private String emailCheckToken;
    private String certificationNumber;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;

    public static User fromAccount(UserAccount account) {
        return new User(
                account.getId(),
                account.getEmail(),
                account.getPassword(),
                account.getNickname(),
                account.getGreeting(),
                account.getProfile_image(),
                account.isEmailVerified(),
                account.getEmailCheckToken(),
                account.getEmailCheckToken(),
                account.getCreatedAt(),
                account.getModifiedAt(),
                account.getDeletedAt()
        );
    }
}
