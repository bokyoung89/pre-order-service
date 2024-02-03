package com.bokyoung.preorderservice.service;

import com.bokyoung.preorderservice.exception.ErrorCode;
import com.bokyoung.preorderservice.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.model.NewsFeedArgs;
import com.bokyoung.preorderservice.model.NewsFeedType;
import com.bokyoung.preorderservice.model.entity.FollowEntity;
import com.bokyoung.preorderservice.model.entity.NewsFeedEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import com.bokyoung.preorderservice.repository.FollowRepository;
import com.bokyoung.preorderservice.repository.NewsFeedRepository;
import com.bokyoung.preorderservice.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FollowService {

    private final UserAccountRepository userAccountRepository;
    private final FollowRepository followRepository;

    private final NewsFeedRepository newsFeedRepository;

    @Transactional
    public void addFollow(Long followeeId, String email) {
        //follower user find
        UserAccountEntity follower = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", email)));

        // followee user find
        UserAccountEntity followee = userAccountRepository.findById(followeeId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("User with id %d not found", followeeId)));

        // Check if already following
        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new PreOrderServiceException(ErrorCode.ALREADY_FOLLOW, String.format("%s is already following", followee.getId()));
        }

        //  Check if follow itself
        if (follower.getId().equals(followee.getId())) {
            throw new PreOrderServiceException(ErrorCode.SELF_FOLLOWING, String.format("Cannot follow yourself", followee.getId()));
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
}
