package com.bokyoung.newsFeedService.repository;

import com.bokyoung.newsFeedService.model.entity.FollowEntity;
import com.bokyoung.newsFeedService.model.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    boolean existsByFollowerAndFollowee(UserAccountEntity follower, UserAccountEntity followee);

    List<FollowEntity> findByFollower(UserAccountEntity follower);

    List<FollowEntity> findByFollowee(UserAccountEntity followee);
}
