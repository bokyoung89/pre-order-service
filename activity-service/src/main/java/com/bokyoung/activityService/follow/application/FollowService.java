package com.bokyoung.activityService.follow.application;

import com.bokyoung.activityService.common.global.exception.ErrorCode;
import com.bokyoung.activityService.common.global.exception.PreOrderServiceException;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedArgs;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedCreateRequest;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedFeignClient;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedType;
import com.bokyoung.activityService.common.infra.userAccount.UserAccountFeignClient;
import com.bokyoung.activityService.follow.domain.entity.FollowEntity;
import com.bokyoung.activityService.follow.dao.FollowRepository;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final NewsFeedFeignClient newsFeedFeignClient;
    private final UserAccountFeignClient userAccountFeignClient;

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;


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
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String followerNickname = circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                getFollowerNickname(follower)).apply(1), throwable -> "failure");
        String followeeNickname = circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                getFolloweeNickname(followee)).apply(1), throwable -> "failure");

        circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(followee, NewsFeedType.NEW_FOLLOW, new NewsFeedArgs(followerNickname, followeeNickname, null)))).apply(1),
                throwable -> "failure");
    }


    @Transactional
    public void cancelFollow(Long followeeId, Long followerId) {
        // followee user find
        FollowEntity followEntity = followRepository.findByFolloweeIdAndFollowerId(followeeId, followerId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("followee is not found", followeeId)));

        followRepository.delete(followEntity);
    }

    private String getFollowerNickname(Long userId) {
        return userAccountFeignClient.getUserAccountByPrincipalId(userId)
                .getResult()
                .getNickname();
    }

    private String getFolloweeNickname(Long userId) {
        return userAccountFeignClient.getUserAccountByUserId(userId)
                .getResult()
                .getNickname();
    }
}
