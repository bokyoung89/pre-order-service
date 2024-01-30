package com.bokyoung.preorderservice.service;

import com.bokyoung.preorderservice.exception.ErrorCode;
import com.bokyoung.preorderservice.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.model.entity.FollowEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import com.bokyoung.preorderservice.repository.FollowRepository;
import com.bokyoung.preorderservice.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FollowService {

    private final UserAccountRepository userAccountRepository;
    private final FollowRepository followRepository;

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
    }
}
