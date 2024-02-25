package com.bokyoung.productService.model;

import com.bokyoung.productService.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Product {

    private Long id;

    private Long userId;

    private String name;

    private String content;

    private int price;

    private ProductType productType;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getContent(),
                entity.getPrice(),
                entity.getProductType(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }
}
