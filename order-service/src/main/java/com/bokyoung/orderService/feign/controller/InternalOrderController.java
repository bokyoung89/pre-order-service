package com.bokyoung.orderService.feign.controller;

import com.bokyoung.orderService.dto.response.Response;
import com.bokyoung.orderService.feign.service.InternalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal")
public class InternalOrderController {

    private final InternalOrderService internalPaymentService;

    /**
     * 결제 성공 시 상태변경(결제완료) 메서드 반환
     */
    @PostMapping("/order/statusPayment")
    public Response<Void> updateStatusPayment(@RequestParam Long orderId) {
        internalPaymentService.updateStatusPayment(orderId);
        return Response.success();
    }

    /**
     * 결제 실패 시 상태변경(주문취소) 및 재고 반영 메서드 반환
     */
    @PostMapping("/order/statusCancel")
    public Response<Void> updateStatusCancelAddStock(@RequestParam Long orderId) {
        internalPaymentService.updateStatusCancelAddStock(orderId);
        return Response.success();
    }
}