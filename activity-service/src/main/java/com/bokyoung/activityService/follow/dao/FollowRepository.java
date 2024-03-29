package com.bokyoung.activityService.follow.dao;

import com.bokyoung.activityService.follow.domain.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    boolean existsByFollowerIdAndFolloweeIdAndDeletedAtIsNull(Long followerId, Long followeeId);

    Optional<FollowEntity> findByFolloweeIdAndFollowerId(Long followerId, Long followeeId);

}
