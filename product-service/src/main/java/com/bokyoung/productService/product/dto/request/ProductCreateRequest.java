package com.bokyoung.productService.product.dto.request;

import com.bokyoung.productService.product.domain.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateRequest {
    //상품 및 재고
    private String name;

    private String content;

    private int price;

    private ProductType productType;

    private Integer stockCount;

    public ProductCreateRequest() {
    }
}
