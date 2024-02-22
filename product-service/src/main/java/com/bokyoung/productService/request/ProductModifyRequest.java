package com.bokyoung.productService.request;

import com.bokyoung.productService.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductModifyRequest {

    private String name;

    private String content;

    private int price;

    private ProductType productType;

    public ProductModifyRequest() {
    }
}
