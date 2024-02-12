package com.bokyoung.userService.controller;

import com.bokyoung.userService.controller.response.NewsFeedResponse;
import com.bokyoung.userService.controller.response.Response;
import com.bokyoung.userService.model.UserAccount;
import com.bokyoung.userService.service.NewsFeedService;
import com.bokyoung.userService.util.ClassUtils;
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

    @GetMapping
    public Response<Page<NewsFeedResponse>> newsFeed(Pageable pageable, Authentication authentication) {
        UserAccount userAccount = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), UserAccount.class);
        return Response.success(newsFeedService.newsFeedsList(userAccount.getId(), pageable).map(NewsFeedResponse::fromNewsFeed));
    }
}
