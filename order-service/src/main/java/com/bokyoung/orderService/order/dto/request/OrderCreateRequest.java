package com.bokyoung.orderService.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateRequest {

    private Long productId;

    private int quantity;

    private String address;

    public OrderCreateRequest() {
    }

}
