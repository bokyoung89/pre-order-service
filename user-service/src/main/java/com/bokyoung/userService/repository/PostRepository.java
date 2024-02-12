package com.bokyoung.userService.repository;

import com.bokyoung.userService.model.entity.FollowEntity;
import com.bokyoung.userService.model.entity.PostEntity;
import com.bokyoung.userService.model.entity.UserAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByUserAccount(UserAccountEntity entity, Pageable pageable);

    Page<PostEntity> findAllByUserAccountInOrderByCreatedAtDesc(List<FollowEntity> userAccounts, Pageable pageable);

}
