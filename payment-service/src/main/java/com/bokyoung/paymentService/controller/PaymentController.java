package com.bokyoung.paymentService.controller;

import com.bokyoung.paymentService.dto.request.PaymentCreateRequest;
import com.bokyoung.paymentService.dto.response.Response;
import com.bokyoung.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-service/payments")
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
