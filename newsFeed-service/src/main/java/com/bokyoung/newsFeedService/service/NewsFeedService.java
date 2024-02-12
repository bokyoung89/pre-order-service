package com.bokyoung.newsFeedService.service;

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

    public Page<NewsFeed> newsFeedsList(Long userId, Pageable pageable) {
        return newsFeedRepository.findAllByUserAccountId(userId, pageable).map(NewsFeed::fromEntity);
    }
}
