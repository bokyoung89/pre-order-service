package com.bokyoung.newsFeedService.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.sql.Timestamp;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"like_comment\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE like_comment SET deleted_at = NOW() WHERE id = ?")
public class LikeCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccount;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void modifiedAt() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }

    public static LikeCommentEntity of(UserAccountEntity userEntity, CommentEntity commentEntity) {
        LikeCommentEntity entity = new LikeCommentEntity();
        entity.setUserAccount(userEntity);
        entity.setComment(commentEntity);
        return entity;
    }

}
