package com.bokyoung.preorderservice.model.entity;

import com.bokyoung.preorderservice.model.AlarmArgs;
import com.bokyoung.preorderservice.model.AlarmType;
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
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() WHERE id = ?")
public class AlramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람을 받은 사람
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccount;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private AlarmArgs alarmArgs;

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

    public static AlramEntity of(UserAccountEntity userEntity, AlarmType alarmType, AlarmArgs args) {
        AlramEntity entity = new AlramEntity();
        entity.setUserAccount(userEntity);
        entity.setAlarmType(alarmType);
        entity.setAlarmArgs(args);
        return entity;
    }

}
