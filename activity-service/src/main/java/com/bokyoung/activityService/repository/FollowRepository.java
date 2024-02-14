package com.bokyoung.activityService.repository;

import com.bokyoung.activityService.model.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    Optional<FollowEntity> findByFolloweeIdAndFollowerId(Long followerId, Long followeeId);

}
