package com.bokyoung.userService.model;

import com.bokyoung.userService.model.entity.NewsFeedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class NewsFeed {
    private Long id;
    private UserAccount userAccount;
    private NewsFeedType newsFeedType;
    private NewsFeedArgs newsFeedArgs;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;

    public static NewsFeed fromEntity(NewsFeedEntity entity) {
        return new NewsFeed(
                entity.getId(),
                UserAccount.fromEntity(entity.getUserAccount()),
                entity.getNewsFeedType(),
                entity.getNewsFeedArgs(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }

}
