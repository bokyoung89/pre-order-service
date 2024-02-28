package com.bokyoung.activityService.dao.repository;

import com.bokyoung.activityService.domain.entity.CommentEntity;
import com.bokyoung.activityService.domain.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

}
