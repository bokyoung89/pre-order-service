package com.bokyoung.newsFeedService.dao.repository;

import com.bokyoung.newsFeedService.domain.entity.NewsFeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedRepository extends JpaRepository<NewsFeedEntity, Long> {

    Page<NewsFeedEntity> findAllByUserId(Long userId, Pageable pageable);
}
