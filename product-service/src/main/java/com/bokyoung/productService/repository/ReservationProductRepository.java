package com.bokyoung.productService.repository;

import com.bokyoung.productService.model.entity.ReservationProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProductRepository extends JpaRepository<ReservationProductEntity, Long> {
}
