package com.bokyoung.newsFeedService.application.controller;

import com.bokyoung.newsFeedService.dto.response.NewsFeedResponse;
import com.bokyoung.newsFeedService.dto.response.Response;
import com.bokyoung.newsFeedService.application.service.NewsFeedService;
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
