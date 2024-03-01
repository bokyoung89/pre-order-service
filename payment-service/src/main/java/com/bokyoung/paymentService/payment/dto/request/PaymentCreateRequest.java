package com.bokyoung.paymentService.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCreateRequest {

    private Long orderId;

    public PaymentCreateRequest() {
    }
}
