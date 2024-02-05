package com.bokyoung.preorderservice.controller.response;

import com.bokyoung.preorderservice.model.NewsFeed;
import com.bokyoung.preorderservice.model.NewsFeedArgs;
import com.bokyoung.preorderservice.model.NewsFeedType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class NewsFeedResponse {
//    private Long id;
//    private NewsFeedType newsFeedType;
//    private NewsFeedArgs newsFeedArgs;
    private String text;
//    private Timestamp createdAt;
//    private Timestamp modifiedAt;
//    private Timestamp deletedAt;

    public static NewsFeedResponse fromNewsFeed(NewsFeed newsFeed) {
        String formattedText = String.format(
                newsFeed.getNewsFeedType().getNewsFeedText(),
                newsFeed.getNewsFeedArgs().getFromUserName(),
                newsFeed.getNewsFeedArgs().getPostTitle()
        );

        return new NewsFeedResponse(
                formattedText // 형식화된 문자열로 설정
        );
    }

}
