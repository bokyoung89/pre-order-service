package com.bokyoung.stockService.stock.application;

import com.bokyoung.stockService.global.exception.ErrorCode;
import com.bokyoung.stockService.global.exception.PreOrderServiceException;
import com.bokyoung.stockService.stock.domain.model.ProductStock;
import com.bokyoung.stockService.stock.domain.model.SalesStatus;
import com.bokyoung.stockService.stock.domain.entity.ProductStockEntity;
import com.bokyoung.stockService.stock.dao.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductStockService {

    private final ProductStockRepository productStockRepository;

    @Transactional
    @Cacheable(value = "productStock", key = "#productId")
    public Integer getStockCount(Long productId) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);
        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    @Transactional
    @CachePut(value = "productStock", key = "#productId")
    public Integer modify(Long productId, Integer stockCount) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);

        productStockEntity.setStockCount(stockCount);

        if(stockCount == 0) {
            productStockEntity.setSalesStatus(SalesStatus.SOLD_OUT);
        } else {
            productStockEntity.setSalesStatus(SalesStatus.ON);
        }

        return ProductStock.fromEntity(productStockEntity).getStockCount();
    }

    private ProductStockEntity getProductStockEntityOrException(Long productId) {
        return productStockRepository.findByProductId(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productId)));
    }
}
