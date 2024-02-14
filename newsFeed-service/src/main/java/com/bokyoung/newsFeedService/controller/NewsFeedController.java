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

    //TODO : 추후 api-gateway 인증 필터 구현해 인증된 사용자 정보 받을 것 (Long principalId)
    @GetMapping
    public Response<Page<NewsFeedResponse>> newsFeed(Pageable pageable, @RequestHeader(name = "principalEmail") String principalEmail) {
        //TODO : Implement the rest of the functionality
        return Response.success(newsFeedService.newsFeedsList(principalEmail, pageable).map(NewsFeedResponse::fromNewsFeed));
    }
}
