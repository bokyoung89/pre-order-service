package com.bokyoung.preorderservice.model;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AlarmArgs {
    //알림을 발생시킨 사람
    private Long fromUserId;
    private Long targetId;

    public AlarmArgs() {
    }

    public AlarmArgs(Long fromUserId, Long targetId) {
        this.fromUserId = fromUserId;
        this.targetId = targetId;
    }
}
