package com.bokyoung.activityService.model;

import com.bokyoung.activityService.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Comment {

    private Long id;

    private String comment;

//    private String nickName;

    private Long postId;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    //TODO : Implement the rest of the functionality
    public static Comment fromAccount(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
//                entity.getNickName(),
                entity.getPost().getId(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }

}
