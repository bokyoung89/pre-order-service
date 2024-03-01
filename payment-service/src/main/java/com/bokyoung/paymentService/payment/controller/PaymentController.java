package com.bokyoung.paymentService.payment.controller;

import com.bokyoung.paymentService.payment.dto.request.PaymentCreateRequest;
import com.bokyoung.paymentService.payment.dto.response.Response;
import com.bokyoung.paymentService.payment.application.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제요청 API
     */
    @PostMapping
    public Response<Void> createPayment(@RequestBody PaymentCreateRequest request,
                                        @RequestHeader(name = "principalId") Long principalId) {
        paymentService.createPayment(request.getOrderId(), principalId);
        return Response.success();
    }
}
