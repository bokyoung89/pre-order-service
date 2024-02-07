package com.bokyoung.activityService.repository;

import com.bokyoung.activityService.model.entity.NewsFeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedRepository extends JpaRepository<NewsFeedEntity, Long> {

    Page<NewsFeedEntity> findAllByUserAccountId(Long userId, Pageable pageable);
}
