package com.bokyoung.activityService.comment.dao;

import com.bokyoung.activityService.comment.domain.entity.CommentEntity;
import com.bokyoung.activityService.post.domain.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

}
