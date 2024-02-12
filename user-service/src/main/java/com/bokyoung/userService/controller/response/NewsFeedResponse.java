package com.bokyoung.userService.controller.response;

import com.bokyoung.userService.model.NewsFeed;
import lombok.AllArgsConstructor;
import lombok.Data;

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
