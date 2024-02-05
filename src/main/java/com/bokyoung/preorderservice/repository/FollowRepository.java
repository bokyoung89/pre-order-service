package com.bokyoung.preorderservice.repository;

import com.bokyoung.preorderservice.model.entity.FollowEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    boolean existsByFollowerAndFollowee(UserAccountEntity follower, UserAccountEntity followee);

    List<FollowEntity> findByFollower(UserAccountEntity follower);

    List<FollowEntity> findByFollowee(UserAccountEntity followee);
}
