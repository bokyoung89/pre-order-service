package com.bokyoung.activityService.dao.repository;

import com.bokyoung.activityService.domain.entity.CommentEntity;
import com.bokyoung.activityService.domain.entity.LikeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeCommentEntity, Long> {

    Optional<LikeCommentEntity> findByUserIdAndComment(Long userId, CommentEntity comment);

    @Query(value = "SELECT COUNT(*) FROM LikeCommentEntity entity where entity.comment =:comment")
    Integer countByComment(@Param("comment") CommentEntity comment);

    List<LikeCommentEntity> findAllByComment(CommentEntity comment);
}
