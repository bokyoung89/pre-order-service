package com.bokyoung.preorderservice.model;

import com.bokyoung.preorderservice.model.entity.AlramEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class Alarm {
    private Long id;
    private UserAccount userAccount;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlramEntity entity) {
        return new Alarm(
                entity.getId(),
                UserAccount.fromEntity(entity.getUserAccount()),
                entity.getAlarmType(),
                entity.getAlarmArgs(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }

}
