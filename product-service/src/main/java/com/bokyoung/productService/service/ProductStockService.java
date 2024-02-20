package com.bokyoung.productService.service;

import com.bokyoung.productService.exception.ErrorCode;
import com.bokyoung.productService.exception.PreOrderServiceException;
import com.bokyoung.productService.model.ProductStock;
import com.bokyoung.productService.model.SalesStatus;
import com.bokyoung.productService.model.entity.ProductStockEntity;
import com.bokyoung.productService.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductStockService {

    private final ProductStockRepository productStockRepository;

    @Transactional
    public ProductStock getStockCount(Long stockId) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(stockId);
        return ProductStock.fromEntity(productStockEntity);
    }

    @Transactional
    public ProductStock modify(Long stockId, Integer stockCount) {
        ProductStockEntity productStockEntity = getProductStockEntityOrException(stockId);

        productStockEntity.setStockCount(stockCount);

        if(stockCount == 0) {
            productStockEntity.setSalesStatus(SalesStatus.SOLD_OUT);
        } else {
            productStockEntity.setSalesStatus(SalesStatus.ON);
        }

        return ProductStock.fromEntity(productStockRepository.save(productStockEntity));
    }

    private ProductStockEntity getProductStockEntityOrException(Long productStockId) {
        return productStockRepository.findById(productStockId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productStockId)));
    }
}
