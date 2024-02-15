package com.bokyoung.activityService.service;

import com.bokyoung.activityService.client.*;
import com.bokyoung.activityService.controller.response.Response;
import com.bokyoung.activityService.controller.response.UserResponse;
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
    private final UserAccountFeignClient userAccountFeignClient;

    @Transactional
    public void addFollow(Long followee, Long follower) {
        // Check if already following
        if (followRepository.existsByFollowerIdAndFolloweeIdAndDeletedAtIsNull(follower, followee)) {
            throw new PreOrderServiceException(ErrorCode.ALREADY_FOLLOW, String.format("already following", followee));
        }

        //  Check if follow itself
        if (follower.equals(followee)) {
            throw new PreOrderServiceException(ErrorCode.SELF_FOLLOWING, String.format("Cannot follow yourself", follower));
        }

        //add follow
        FollowEntity followEntity = FollowEntity.of(follower, followee);
        followRepository.save(followEntity);

        //나를 팔로우하는 소식 -> 뉴스피드 저장
        String followerNickname = userAccountFeignClient.getUserAccountByPrincipalId(follower).getResult().getNickname();
        String followeeNickname = userAccountFeignClient.getUserAccountByUserId(followee).getResult().getNickname();
        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(followee, NewsFeedType.NEW_FOLLOW, new NewsFeedArgs(followerNickname, followeeNickname, null)));
    }

    @Transactional
    public void cancelFollow(Long followeeId, Long followerId) {
        // followee user find
        FollowEntity followEntity = followRepository.findByFolloweeIdAndFollowerId(followeeId, followerId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("followee is not found", followeeId)));

        followRepository.delete(followEntity);
    }
}
