package com.bokyoung.activityService.post.dto.response;

import com.bokyoung.activityService.post.domain.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUserId(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getDeletedAt()
        );
    }

}
