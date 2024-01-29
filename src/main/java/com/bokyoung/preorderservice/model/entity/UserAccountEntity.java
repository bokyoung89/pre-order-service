package com.bokyoung.preorderservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"user_account\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE user_account SET deleted_at = NOW() WHERE id = ?")
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String greeting;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profile_image;

    private boolean emailVerified; //이메일 인증 여부

    private String emailCheckToken; //이메일 검증 토큰

    private String certificationNumber; //이메일 인증 코드

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());

    }

    @PreUpdate
    void modifiedAt() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }

    public static UserAccountEntity of(String email, String password, String nickname, String greeting, String profile_image, Boolean emailVerified) {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setEmail(email);
        userAccountEntity.setPassword(password);
        userAccountEntity.setNickname(nickname);
        userAccountEntity.setGreeting(greeting);
        userAccountEntity.setProfile_image(profile_image);
        return userAccountEntity;
    }

    public static UserAccountEntity of(String certificationNumber, Boolean emailVerified) {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setCertificationNumber(certificationNumber);
        return userAccountEntity;
    }

    public static UserAccountEntity of(String password, String nickname, String greeting, String profile_image) {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setPassword(password);
        userAccountEntity.setNickname(nickname);
        userAccountEntity.setGreeting(greeting);
        userAccountEntity.setProfile_image(profile_image);
        return userAccountEntity;
    }
}