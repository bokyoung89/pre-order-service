package com.bokyoung.orderService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    ORDER("주문완료"),
    CANCEL("주문취소"),
    PAYMENT("결제완료")
    ;

    private final String OrderStatusText;
}
