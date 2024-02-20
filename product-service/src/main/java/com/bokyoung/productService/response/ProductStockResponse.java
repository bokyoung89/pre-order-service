package com.bokyoung.productService.response;

import com.bokyoung.productService.model.ProductStock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductStockResponse {

    private Integer stockCount;

    private String salesStatus;

    public static ProductStockResponse fromProductStock(ProductStock productStock) {
        return new ProductStockResponse(
                productStock.getStockCount(),
                productStock.getSalesStatus().getSalesStatusText()
        );
    }

}
