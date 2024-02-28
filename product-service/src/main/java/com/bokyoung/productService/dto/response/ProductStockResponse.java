package com.bokyoung.productService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductStockResponse {

    private Integer stockCount;

    public static ProductStockResponse fromProductStock(Integer stockCount) {
        return new ProductStockResponse(stockCount);
    }
}
