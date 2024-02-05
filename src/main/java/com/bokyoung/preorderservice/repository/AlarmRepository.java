package com.bokyoung.preorderservice.repository;

import com.bokyoung.preorderservice.model.entity.AlramEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlramEntity, Long> {

    Page<AlramEntity> findAllByUserAccount(UserAccountEntity userAccount, Pageable pageable);
}
