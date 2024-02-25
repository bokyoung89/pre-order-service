package com.bokyoung.productService.repository;

import com.bokyoung.productService.model.entity.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStockEntity, Long> {
}
