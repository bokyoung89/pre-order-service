package com.bokyoung.preorderservice.model;

import com.bokyoung.preorderservice.model.entity.FollowEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Follow {
    private Long id;

    private UserAccount follower;

    private UserAccount followee;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static Follow fromEntity(FollowEntity entity) {
        return new Follow(
                entity.getId(),
                UserAccount.fromEntity(entity.getFollower()),
                UserAccount.fromEntity(entity.getFollowee()),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }
}
