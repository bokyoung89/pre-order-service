package com.bokyoung.stockService.model;

import com.bokyoung.stockService.model.entity.ProductStockEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductStock {

    private Long id;

    private Long productId;

    private ProductType productType;

    private Integer stockCount;

    private SalesStatus salesStatus;

    public ProductStock() {}

    public static ProductStock fromEntity(ProductStockEntity entity) {
        return new ProductStock(
                entity.getId(),
                entity.getProductId(),
                entity.getProductType(),
                entity.getStockCount(),
                entity.getSalesStatus()
        );
    }
}
