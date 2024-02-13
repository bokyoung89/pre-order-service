package com.bokyoung.newsFeedService.service;

import com.bokyoung.newsFeedService.client.UserAccountFeignClient;
import com.bokyoung.newsFeedService.controller.response.Response;
import com.bokyoung.newsFeedService.controller.response.UserResponse;
import com.bokyoung.newsFeedService.exception.ErrorCode;
import com.bokyoung.newsFeedService.exception.PreOrderServiceException;
import com.bokyoung.newsFeedService.repository.NewsFeedRepository;
import com.bokyoung.newsFeedService.model.NewsFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final UserAccountFeignClient userAccountFeignClient;

    public Page<NewsFeed> newsFeedsList(String email, Pageable pageable) {
        Response<UserResponse> userResponse = userAccountFeignClient.getUserAccount(email);
        return newsFeedRepository.findAllByUserId(userResponse.getResult().getId(), pageable).map(NewsFeed::fromEntity);
    }
}
