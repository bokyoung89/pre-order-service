package com.bokyoung.newsFeedService.controller;

import com.bokyoung.newsFeedService.controller.response.NewsFeedResponse;
import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.controller.response.UserAccountResponse;
import com.bokyoung.newsFeedService.service.NewsFeedService;
import com.bokyoung.newsFeedService.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    //TODO: Implement the rest of the functionality
    @GetMapping
    public Response<Page<NewsFeedResponse>> newsFeed(Pageable pageable, Authentication authentication) {
        return null;
//        UserAccountResponse userAccountResponse = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), UserAccountResponse.class);
//        return Response.success(newsFeedService.newsFeedsList(UserAccountResponse.getId(), pageable).map(NewsFeedResponse::fromNewsFeed));
    }
}
