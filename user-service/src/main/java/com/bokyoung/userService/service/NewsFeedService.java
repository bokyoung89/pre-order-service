package com.bokyoung.userService.service;

import com.bokyoung.userService.model.NewsFeed;
import com.bokyoung.userService.repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;

    public Page<NewsFeed> newsFeedsList(Long userId, Pageable pageable) {
        return newsFeedRepository.findAllByUserAccountId(userId, pageable).map(NewsFeed::fromEntity);
    }
}
