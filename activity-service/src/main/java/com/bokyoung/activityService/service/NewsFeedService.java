package com.bokyoung.activityService.service;

import com.bokyoung.activityService.model.NewsFeed;
import com.bokyoung.activityService.repository.NewsFeedRepository;
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
