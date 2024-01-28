package com.bokyoung.preorderservice.model;

import com.bokyoung.preorderservice.model.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
    }
}
