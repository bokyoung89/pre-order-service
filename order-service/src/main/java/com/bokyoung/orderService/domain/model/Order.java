package com.bokyoung.orderService.domain.model;

import com.bokyoung.orderService.domain.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Order {

    private Long id;

    private Long productId;

    private Long userId;

    private int quantity;

    private String address;

    private OrderStatus orderStatus;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;


    public static Order fromEntity(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getProductId(),
                entity.getUserId(),
                entity.getQuantity(),
                entity.getAddress(),
                entity.getOrderStatus(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getDeletedAt()
        );
    }
}
