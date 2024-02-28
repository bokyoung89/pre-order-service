package com.bokyoung.stockService.dao.repository;

import com.bokyoung.stockService.domain.model.ProductStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@EnableCaching
public class ProductStockCacheRepository {

    private final RedisTemplate<String, ProductStock> ProductStockRedisTemplate;
    private final static Duration PRODUCT_STOCK_CACHE_TTL = Duration.ofDays(3);

    public void setProductStock(ProductStock ProductStock) {
        String key = getKey(ProductStock.getProductId());
        ProductStockRedisTemplate.opsForValue().set(key, ProductStock);
        log.info("Set ProductStock to Redis {} : {}", key, ProductStock);
    }

    public Optional<ProductStock> getProductStock(Long productId) {
        String key = getKey(productId);
        ProductStock productStock = ProductStockRedisTemplate.opsForValue().get(key);
        log.info("Get ProductStock to Redis {} : {}", key, productStock);
        return Optional.ofNullable(productStock);
    }

    private String getKey(Long productId) {
        return "ProductStock:" + productId;
    }
}
