package com.bokyoung.newsFeedService.model;

import com.bokyoung.newsFeedService.model.entity.UserAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class UserAccount implements UserDetails {

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

    public static UserAccount fromEntity(UserAccountEntity entity) {
        return new UserAccount(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getGreeting(),
                entity.getProfile_image(),
                entity.isEmailVerified(),
                entity.getEmailCheckToken(),
                entity.getEmailCheckToken(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
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
