package com.bokyoung.preorderservice.model;

import com.bokyoung.preorderservice.model.entity.CommentEntity;
import com.bokyoung.preorderservice.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Comment {

    private Long id;

    private String comment;

    private String nickName;

    private Long postId;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static Comment fromAccount(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUserAccount().getNickname(),
                entity.getPost().getId(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }

}