package com.bokyoung.activityService.repository;

import com.bokyoung.activityService.controller.response.UserAccountResponse;
import com.bokyoung.activityService.model.entity.FollowEntity;
import com.bokyoung.activityService.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByUserId(Long userId, Pageable pageable);

    Page<PostEntity> findAllByUserIdInOrderByCreatedAtDesc(List<FollowEntity> userAccounts, Pageable pageable);

}
