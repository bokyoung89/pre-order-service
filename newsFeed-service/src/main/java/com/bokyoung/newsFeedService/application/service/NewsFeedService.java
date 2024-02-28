package com.bokyoung.newsFeedService.application.service;

import com.bokyoung.newsFeedService.domain.model.NewsFeedArgs;
import com.bokyoung.newsFeedService.domain.model.NewsFeedType;
import com.bokyoung.newsFeedService.domain.entity.NewsFeedEntity;
import com.bokyoung.newsFeedService.dao.repository.NewsFeedRepository;
import com.bokyoung.newsFeedService.domain.model.NewsFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;

    public Page<NewsFeed> newsFeedsList(Long userId, Pageable pageable) {
        return newsFeedRepository.findAllByUserId(userId, pageable).map(NewsFeed::fromEntity);
    }

    public void create(Long userId, NewsFeedArgs newsFeedArgs, NewsFeedType newsFeedType) {
        newsFeedRepository.save(NewsFeedEntity.of(userId, newsFeedType, newsFeedArgs));
    }
}
