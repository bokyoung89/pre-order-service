package com.bokyoung.productService.model;

import com.bokyoung.productService.model.entity.ProductStockEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductStock {

    private Integer stockCount;

    private SalesStatus salesStatus;

    public static ProductStock fromEntity(ProductStockEntity entity) {
        return new ProductStock(
                entity.getStockCount(),
                entity.getSalesStatus()
        );
    }
}
