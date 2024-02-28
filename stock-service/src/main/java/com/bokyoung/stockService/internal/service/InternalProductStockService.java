package com.bokyoung.stockService.internal.service;

import com.bokyoung.stockService.global.exception.ErrorCode;
import com.bokyoung.stockService.global.exception.PreOrderServiceException;
import com.bokyoung.stockService.domain.model.ProductStock;
import com.bokyoung.stockService.domain.model.ProductType;
import com.bokyoung.stockService.domain.model.SalesStatus;
import com.bokyoung.stockService.domain.entity.ProductStockEntity;
import com.bokyoung.stockService.dao.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InternalProductStockService {

    private final ProductStockRepository productStockRepository;

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer createStock(Long productId, ProductType productType, Integer stockCount) {
        ProductStockEntity productStockEntity = ProductStockEntity.of(productId, productType, stockCount);

        if(productStockEntity.getStockCount() == 0) {
            throw new PreOrderServiceException(ErrorCode.STOCK_COUNT_IS_ZERO, String.format("StockCount is zero"));
        }

        productStockRepository.save(productStockEntity);
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }
    @Transactional
    @Cacheable(value = "productStock", key = "#productId")
    public Integer getStock(Long productId) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer reduceStock(Long productId, Integer quantity) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        productStockEntity.setStockCount(productStockEntity.getStockCount() - quantity);

        if(productStockEntity.getStockCount() == 0) {
            productStockEntity.setSalesStatus(SalesStatus.SOLD_OUT);
        }

        productStockRepository.save(productStockEntity);
        //캐시 데이터 반영을 위한 반환값
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer increaseStock(Long productId, Integer quantity) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        productStockEntity.setStockCount(productStockEntity.getStockCount() + quantity);
        productStockRepository.save(productStockEntity);
        //캐시 데이터 반영을 위한 반환값
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer modifyStock(Long productId, Integer quantity) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        productStockEntity.setStockCount(quantity);

        if(productStockEntity.getStockCount() == 0) {
            productStockEntity.setSalesStatus(SalesStatus.SOLD_OUT);
        }

        productStockRepository.save(productStockEntity);
        //캐시 데이터 반영을 위한 반환값
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    @Transactional
    @CacheEvict(value = "productStock", key = "#productId")
    public void deleteStock(Long productId) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        productStockRepository.delete(productStockEntity);
    }

    private ProductStockEntity getProductStockEntityOrException(Long productId) {
        return productStockRepository.findByProductId(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productId)));
    }
}
