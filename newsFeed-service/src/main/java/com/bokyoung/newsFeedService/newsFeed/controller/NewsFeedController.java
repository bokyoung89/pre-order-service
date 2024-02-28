package com.bokyoung.newsFeedService.newsFeed.controller;

import com.bokyoung.newsFeedService.newsFeed.dto.response.NewsFeedResponse;
import com.bokyoung.newsFeedService.newsFeed.dto.response.Response;
import com.bokyoung.newsFeedService.newsFeed.application.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    // TODO : implement
    @GetMapping
    public Response<Page<NewsFeedResponse>> newsFeed(Pageable pageable, @RequestHeader(name = "principalId") Long principalId) {
        return Response.success(newsFeedService.newsFeedsList(principalId, pageable).map(NewsFeedResponse::fromNewsFeed));
    }
}
