package com.bokyoung.productService.repository;

import com.bokyoung.productService.model.entity.ReservationProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProductStockRepository extends JpaRepository<ReservationProductStockEntity, Long> {
}
