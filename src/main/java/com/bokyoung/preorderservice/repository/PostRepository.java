package com.bokyoung.preorderservice.repository;

import com.bokyoung.preorderservice.model.entity.PostEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByUserAccount(UserAccountEntity entity, Pageable pageable);

}
