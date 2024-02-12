package com.bokyoung.activityService.controller.response;

import com.bokyoung.activityService.model.Post;
import com.bokyoung.activityService.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private UserAccount userAccount;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUserAccount(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getDeletedAt()
        );
    }

}
