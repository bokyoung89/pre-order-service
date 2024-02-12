package com.bokyoung.activityService.repository;

import com.bokyoung.activityService.model.entity.CommentEntity;
import com.bokyoung.activityService.model.entity.LikeCommentEntity;
import com.bokyoung.activityService.model.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeCommentEntity, Long> {

    Optional<LikeCommentEntity> findByUserAccountAndComment(UserAccountEntity userAccount, CommentEntity comment);

    @Query(value = "SELECT COUNT(*) FROM LikeCommentEntity entity where entity.comment =:comment")
    Integer countByComment(@Param("comment") CommentEntity comment);

    List<LikeCommentEntity> findAllByComment(CommentEntity comment);
}
