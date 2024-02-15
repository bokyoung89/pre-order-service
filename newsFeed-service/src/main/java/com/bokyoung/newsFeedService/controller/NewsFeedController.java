package com.bokyoung.newsFeedService.controller;

import com.bokyoung.newsFeedService.controller.response.NewsFeedResponse;
import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.service.NewsFeedService;
import com.bokyoung.newsFeedService.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    //TODO : 추후 api-gateway 인증 필터 구현해 인증된 사용자 정보 받을 것 (Long principalId)
    @GetMapping
    public Response<Page<NewsFeedResponse>> newsFeed(Pageable pageable, Long principalId) {
        //TODO : Implement the rest of the functionality
//        UserAccount userAccount = ClassUtils.getSafeCastInstance(principalId, UserAccount.class);
//        return Response.success(newsFeedService.newsFeedsList(userAccount.getId(), pageable).map(NewsFeedResponse::fromNewsFeed));
        return null;
    }
}
