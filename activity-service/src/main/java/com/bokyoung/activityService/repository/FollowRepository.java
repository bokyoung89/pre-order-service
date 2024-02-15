package com.bokyoung.activityService.repository;

import com.bokyoung.activityService.model.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    List<FollowEntity> findByFollowerId(Long followerId);

    List<FollowEntity> findByFolloweeId(Long followeeId);
}
