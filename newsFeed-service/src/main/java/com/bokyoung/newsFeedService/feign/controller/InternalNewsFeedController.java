package com.bokyoung.newsFeedService.feign.controller;

import com.bokyoung.newsFeedService.controller.request.NewsFeedCreateRequest;
import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal")
public class InternalNewsFeedController {

    private final NewsFeedService newsFeedService;

    @PostMapping("/newsFeed")
    public Response<Void> createNewsFeed(@RequestBody NewsFeedCreateRequest request) {
        newsFeedService.create(request.getUserId(), request.getNewsFeedArgs(), request.getNewsFeedType());
        return Response.success();
    }
}
