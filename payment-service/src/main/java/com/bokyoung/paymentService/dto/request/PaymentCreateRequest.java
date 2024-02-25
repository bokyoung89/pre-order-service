package com.bokyoung.paymentService.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCreateRequest {

    private Long orderId;

    public PaymentCreateRequest() {
    }
}
