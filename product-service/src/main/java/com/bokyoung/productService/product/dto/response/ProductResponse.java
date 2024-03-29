package com.bokyoung.productService.product.dto.response;

import com.bokyoung.productService.product.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductResponse {

    private Long id;

    private String name;

    private String content;

    private int price;

    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getContent(),
                product.getPrice()
        );
    }
}
