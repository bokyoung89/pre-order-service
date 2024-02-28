package com.bokyoung.stockService.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductStockModifyRequest {

    private Integer stockCount;

    public ProductStockModifyRequest() {
    }
}
