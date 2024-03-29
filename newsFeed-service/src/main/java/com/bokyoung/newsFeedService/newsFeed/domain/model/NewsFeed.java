package com.bokyoung.newsFeedService.newsFeed.domain.model;

import com.bokyoung.newsFeedService.newsFeed.domain.entity.NewsFeedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class NewsFeed {
    private Long id;
    private Long userId;
    private NewsFeedType newsFeedType;
    private NewsFeedArgs newsFeedArgs;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;

    public static NewsFeed fromEntity(NewsFeedEntity entity) {
        return new NewsFeed(
                entity.getId(),
                entity.getUserId(),
                entity.getNewsFeedType(),
                entity.getNewsFeedArgs(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }

}
