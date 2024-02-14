package com.bokyoung.newsFeedService.controller;

import com.bokyoung.newsFeedService.controller.response.NewsFeedResponse;
import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.service.NewsFeedService;
import com.bokyoung.newsFeedService.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsFeed-service/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    // TODO : implement
    @GetMapping
    public Response<Page<NewsFeedResponse>> newsFeed(Pageable pageable, @RequestHeader(name = "principalId") Long principalId) {
        return Response.success(newsFeedService.newsFeedsList(principalId, pageable).map(NewsFeedResponse::fromNewsFeed));
    }
}
