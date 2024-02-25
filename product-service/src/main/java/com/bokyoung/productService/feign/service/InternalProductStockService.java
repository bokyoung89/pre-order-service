package com.bokyoung.productService.feign.service;

import com.bokyoung.productService.exception.ErrorCode;
import com.bokyoung.productService.exception.PreOrderServiceException;
import com.bokyoung.productService.model.ProductStock;
import com.bokyoung.productService.model.SalesStatus;
import com.bokyoung.productService.model.entity.ProductStockEntity;
import com.bokyoung.productService.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InternalProductStockService {

    private final ProductStockRepository productStockRepository;

    @Transactional
    @Cacheable(value = "productStock", key = "#productId")
    public Integer getProductStockCount(Long productId) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    private ProductStockEntity getProductStockEntityOrException(Long productId) {
        return productStockRepository.findByProductId(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productId)));
    }

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer removeStockCount(Long productId, Integer quantity) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        productStockEntity.setStockCount(productStockEntity.getStockCount() - quantity);

        if(productStockEntity.getStockCount() == 0) {
            productStockEntity.setSalesStatus(SalesStatus.SOLD_OUT);
        }
        //캐시 데이터 반영을 위한 반환값
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer addStockCount(Long productId, Integer quantity) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        productStockEntity.setStockCount(productStockEntity.getStockCount() + quantity);
        //캐시 데이터 반영을 위한 반환값
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }
}
