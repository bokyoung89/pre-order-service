package com.bokyoung.activityService.common.infra.newsFeed;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsFeedArgs {
    private String fromUserName; //알림을 발생시킨 사람
    private String toUserName; //알림을 받는 사람
    private String postTitle;


    public NewsFeedArgs() {
    }
}
