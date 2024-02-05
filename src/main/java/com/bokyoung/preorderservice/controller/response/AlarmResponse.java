package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.Alarm;
import com.bokyoung.preorderservice.model.AlarmArgs;
import com.bokyoung.preorderservice.model.AlarmType;
import com.bokyoung.preorderservice.model.UserAccount;
import com.bokyoung.preorderservice.model.entity.AlramEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AlarmResponse {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private String text;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;

    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType(),
                alarm.getAlarmArgs(),
                alarm.getAlarmType().getAlarmText(),
                alarm.getCreatedAt(),
                alarm.getModifiedAt(),
                alarm.getDeletedAt()
        );
    }

}
