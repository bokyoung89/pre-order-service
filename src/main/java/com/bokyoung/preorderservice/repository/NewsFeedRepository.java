package com.bokyoung.preorderservice.repository;

import com.bokyoung.preorderservice.model.entity.NewsFeedEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedRepository extends JpaRepository<NewsFeedEntity, Long> {

    Page<NewsFeedEntity> findAllByUserAccountId(Long userId, Pageable pageable);
}
