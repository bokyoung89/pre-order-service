package com.bokyoung.orderService.client;

import com.bokyoung.orderService.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/internal/stocks/count")
    Integer getProductStockCount(@RequestParam Long productId);

    @PostMapping("/api/internal/stocks/remove")
    Response<Void> removeStockCount(@RequestParam Long productId,
                                    @RequestParam Integer quantity);

    @PostMapping("/api/internal/stocks/add")
    Response<Void> addStockCount(@RequestParam Long productId,
                                 @RequestParam Integer quantity);
}
