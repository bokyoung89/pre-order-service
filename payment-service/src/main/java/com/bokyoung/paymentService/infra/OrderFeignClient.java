package com.bokyoung.paymentService.infra;

import com.bokyoung.paymentService.payment.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @PostMapping("/api/internal/order/statusPayment")
    Response<Void> updateStatusPayment(@RequestParam Long orderId);

    @PostMapping("/api/internal/order/statusCancel")
    Response<Void> updateStatusCancelAddStock(@RequestParam Long orderId);
}
