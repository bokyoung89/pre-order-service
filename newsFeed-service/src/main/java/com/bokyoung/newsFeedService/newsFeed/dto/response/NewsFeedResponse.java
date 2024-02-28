package com.bokyoung.newsFeedService.newsFeed.dto.response;

import com.bokyoung.newsFeedService.newsFeed.domain.model.NewsFeed;
import com.bokyoung.newsFeedService.newsFeed.domain.model.NewsFeedType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsFeedResponse {
    private String text;

    public static NewsFeedResponse fromNewsFeed(NewsFeed newsFeed) {
        // NEW_COMMENT_ON_POST 또는 NEW_LIKE_ON_POST 타입인 경우
        if(newsFeed.getNewsFeedType() == NewsFeedType.NEW_COMMENT_ON_POST || newsFeed.getNewsFeedType() == NewsFeedType.NEW_LIKE_ON_POST) {
            String formattedText = String.format(
                    newsFeed.getNewsFeedType().getNewsFeedText(),
                    newsFeed.getNewsFeedArgs().getFromUserName(),
                    newsFeed.getNewsFeedArgs().getPostTitle()
            );
            return new NewsFeedResponse(formattedText);  // 형식화된 문자열로 설정
        } else {
            String formattedText = String.format(
                    newsFeed.getNewsFeedType().getNewsFeedText(),
                    newsFeed.getNewsFeedArgs().getFromUserName(),
                    newsFeed.getNewsFeedArgs().getToUserName()
            );
            return new NewsFeedResponse(formattedText); // 형식화된 문자열로 설정
        }
    }
}
