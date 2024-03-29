package com.bokyoung.activityService.comment.dto.response;

import com.bokyoung.activityService.comment.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class CommentResponse {

    private Long id;

    private String comment;

//    private String nickName;

    private Long postId;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
//                comment.getNickName(),
                comment.getPostId(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getDeletedAt()
        );
    }

}
