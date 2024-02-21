package com.bokyoung.orderService.dto.response;

import com.bokyoung.orderService.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderResponse {

    private Long id;

    private Long productId;

    private Long userId;

    private int quantity;

    private String address;

    private String orderStatus;

    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getUserId(),
                order.getQuantity(),
                order.getAddress(),
                order.getOrderStatus().getOrderStatusText()
        );
    }
}
