package com.bokyoung.orderService.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    ORDER("결제대기"),
    CANCEL("주문취소"),
    PAYMENT("결제완료")
    ;

    private final String OrderStatusText;
}
