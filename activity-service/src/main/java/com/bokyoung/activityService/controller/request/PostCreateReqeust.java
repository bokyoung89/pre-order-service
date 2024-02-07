package com.bokyoung.activityService.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateReqeust {

    private String email;

    private String title;

    private String content;

    public PostCreateReqeust() {
    }
}
