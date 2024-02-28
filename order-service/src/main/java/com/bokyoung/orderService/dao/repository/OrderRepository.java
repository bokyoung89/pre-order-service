package com.bokyoung.orderService.dao.repository;

import com.bokyoung.orderService.domain.entity.OrderEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByUserId(Pageable pageable, Long principalId);
}
