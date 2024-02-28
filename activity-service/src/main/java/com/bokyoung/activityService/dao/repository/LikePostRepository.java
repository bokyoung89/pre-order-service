package com.bokyoung.activityService.dao.repository;

import com.bokyoung.activityService.domain.entity.LikePostEntity;
import com.bokyoung.activityService.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository<LikePostEntity, Long> {

    Optional<LikePostEntity> findByUserIdAndPost(Long userId, PostEntity post);

    @Query(value = "SELECT COUNT(*) FROM LikePostEntity entity where entity.post =:post")
    Integer countByPost(@Param("post") PostEntity post);

    List<LikePostEntity> findAllByPost(PostEntity post);
}
