package com.bokyoung.userService.model.entity;

import com.bokyoung.userService.model.NewsFeedArgs;
import com.bokyoung.userService.model.NewsFeedType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"news_feed\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE \"news_feed\" SET deleted_at = NOW() WHERE id = ?")
public class NewsFeedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람을 받은 사람
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccount;

    @Enumerated(EnumType.STRING)
    private NewsFeedType newsFeedType;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private NewsFeedArgs newsFeedArgs;

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

    public static NewsFeedEntity of(UserAccountEntity userEntity, NewsFeedType newsFeedType, NewsFeedArgs newsFeedArgs) {
        NewsFeedEntity entity = new NewsFeedEntity();
        entity.setUserAccount(userEntity);
        entity.setNewsFeedType(newsFeedType);
        entity.setNewsFeedArgs(newsFeedArgs);
        return entity;
    }

}
