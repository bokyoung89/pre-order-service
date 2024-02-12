package com.bokyoung.userService.model.entity;

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
@Table(name = "\"like_post\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE like_post SET deleted_at = NOW() WHERE id = ?")
public class LikePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

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

    public static LikePostEntity of(UserAccountEntity userEntity, PostEntity postEntity) {
        LikePostEntity entity = new LikePostEntity();
        entity.setUserAccount(userEntity);
        entity.setPost(postEntity);
        return entity;
    }

}
