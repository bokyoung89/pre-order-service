package com.bokyoung.orderService.infra;

import com.bokyoung.orderService.order.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "stock-service")
public interface StockFeignClient {

    @GetMapping("/api/internal/stocks")
    Integer getStock(@RequestParam Long productId);

    @PutMapping("/api/internal/stocks/reduce")
    Response<Void> reduceStock(@RequestParam Long productId,
                                    @RequestParam Integer quantity);

    @PutMapping("/api/internal/stocks/increase")
    Response<Void> increaseStock(@RequestParam Long productId,
                                 @RequestParam Integer quantity);
}
