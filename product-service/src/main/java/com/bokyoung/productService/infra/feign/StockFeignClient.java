package com.bokyoung.productService.infra.feign;

import com.bokyoung.productService.domain.model.ProductType;
import com.bokyoung.productService.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service")
public interface StockFeignClient {

    @PostMapping("/api/internal/stocks")
    Response<Void> createStock(@RequestParam Long productId,
                               @RequestParam ProductType productType,
                               @RequestParam Integer stockCount);

    @PutMapping("/api/internal/stocks/modify")
    Response<Void> modifyStock(@RequestParam Long productId,
                               @RequestParam Integer quantity);

    @DeleteMapping("/api/internal/stocks")
    Response<Void> deleteStock(@RequestParam Long productId);
}
