package com.bokyoung.productService.model;

import com.bokyoung.productService.model.entity.ReservationProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ReservationProduct {

    private Long id;

    private Long userId;

    private String name;

    private String content;

    private int price;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    public static ReservationProduct fromEntity(ReservationProductEntity entity) {
        return new ReservationProduct(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getContent(),
                entity.getPrice(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }
}
