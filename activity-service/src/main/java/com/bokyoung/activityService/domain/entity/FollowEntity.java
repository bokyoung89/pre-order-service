package com.bokyoung.activityService.domain.entity;

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
@Table(name = "\"follow\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE follow SET deleted_at = NOW() WHERE id = ?")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;

    private Long followeeId;

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

    public static FollowEntity of(Long followerId, Long followeeId) {
        FollowEntity entity = new FollowEntity();
        entity.setFollowerId(followerId);
        entity.setFolloweeId(followeeId);
        return entity;
    }

}
