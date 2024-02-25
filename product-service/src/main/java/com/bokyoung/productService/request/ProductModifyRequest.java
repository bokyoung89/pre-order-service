package com.bokyoung.productService.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductModifyRequest {

    private String name;

    private String content;

    private int price;

    public ProductModifyRequest() {
    }
}
