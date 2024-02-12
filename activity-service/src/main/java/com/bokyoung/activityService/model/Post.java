package com.bokyoung.activityService.model;

import com.bokyoung.activityService.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Post {

    private Long id;

    private String title;

    private String content;

    private UserAccount userAccount;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static Post fromAccount(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                UserAccount.fromEntity(entity.getUserAccount()),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }

}
