package com.bokyoung.stockService.repository;

import com.bokyoung.stockService.model.entity.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStockEntity, Long> {


    Optional<ProductStockEntity> findByProductId(Long productId);
}
