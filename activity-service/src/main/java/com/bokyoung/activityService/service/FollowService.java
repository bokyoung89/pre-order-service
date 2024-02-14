package com.bokyoung.activityService.service;

import com.bokyoung.activityService.exception.PreOrderServiceException;
import com.bokyoung.activityService.exception.ErrorCode;
import com.bokyoung.activityService.model.entity.FollowEntity;
import com.bokyoung.activityService.model.entity.PostEntity;
import com.bokyoung.activityService.repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void addFollow(Long followee, Long follower) {
        // followee user find
        followRepository.findById(followee).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("followee is not found", followee)));

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

        //TODO : modify
        //나를 팔로우하는 소식 -> 뉴스피드 저장
        //newsFeedRepository.save(NewsFeedEntity.of(followEntity.getFollowee(), NewsFeedType.NEW_FOLLOW, new NewsFeedArgs(follower.getNickname(), followee.getNickname())));
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
