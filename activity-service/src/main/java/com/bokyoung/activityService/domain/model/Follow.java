package com.bokyoung.activityService.domain.model;

import com.bokyoung.activityService.domain.entity.FollowEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Follow {
    private Long id;

    private Long followerId;

    private Long followeeId;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static Follow fromEntity(FollowEntity entity) {
        return new Follow(
                entity.getId(),
                entity.getFollowerId(),
                entity.getFolloweeId(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }
}
