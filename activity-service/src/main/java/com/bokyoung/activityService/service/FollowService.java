package com.bokyoung.activityService.service;

import com.bokyoung.activityService.client.*;
import com.bokyoung.activityService.controller.response.Response;
import com.bokyoung.activityService.exception.PreOrderServiceException;
import com.bokyoung.activityService.exception.ErrorCode;
import com.bokyoung.activityService.model.entity.FollowEntity;
import com.bokyoung.activityService.repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final NewsFeedFeignClient newsFeedFeignClient;

    @Transactional
    public void addFollow(Long followee, Long follower) {
        // Check if already following
        if (followRepository.existsByFollowerIdAndFolloweeId(follower, followee)) {
            throw new PreOrderServiceException(ErrorCode.ALREADY_FOLLOW, String.format("already following", followee));
        }

        //  Check if follow itself
        if (follower.equals(followee)) {
            throw new PreOrderServiceException(ErrorCode.SELF_FOLLOWING, String.format("Cannot follow yourself", follower));
        }

        //add follow
        FollowEntity followEntity = FollowEntity.of(follower, followee);
        followRepository.save(followEntity);

        //
        /**
         * 나를 팔로우하는 소식 -> 뉴스피드 저장
         * TODO : 뉴스피드에 저장할 닉네임을 user-service에서 호출
         */
        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(followee, NewsFeedType.NEW_FOLLOW, new NewsFeedArgs("userTest1", "userTest2", null)));
        //TODO : implement
        //내 팔로우의 팔로우 소식 -> 뉴스피드 저장
    }

    @Transactional
    public void cancelFollow(Long followeeId, Long followerId) {
        // followee user find
        FollowEntity followEntity = followRepository.findByFolloweeIdAndFollowerId(followeeId, followerId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("followee is not found", followeeId)));

        System.out.println(followEntity.getId());
        followRepository.delete(followEntity);

    }
}
