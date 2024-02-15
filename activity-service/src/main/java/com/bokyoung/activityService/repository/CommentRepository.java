package com.bokyoung.activityService.repository;

import com.bokyoung.activityService.model.entity.CommentEntity;
import com.bokyoung.activityService.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

}
