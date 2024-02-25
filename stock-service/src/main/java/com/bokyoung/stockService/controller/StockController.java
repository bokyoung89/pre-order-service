package com.bokyoung.stockService.controller;

import com.bokyoung.stockService.request.ProductStockModifyRequest;
import com.bokyoung.stockService.response.ProductStockResponse;
import com.bokyoung.stockService.response.Response;
import com.bokyoung.stockService.service.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock-service/stocks")
@RequiredArgsConstructor
public class StockController {

    private final ProductStockService productStockService;

    @GetMapping("/{productId}")
    public Response<ProductStockResponse> getStockCount(@PathVariable(name = "productId") Long productId) {
        Integer productCount = productStockService.getStockCount(productId);
        return Response.success(ProductStockResponse.fromProductStock(productCount));
    }

    @PutMapping("/{productId}")
    public Response<ProductStockResponse> modify(@PathVariable(name = "productId") Long productId,
                                                 @RequestBody ProductStockModifyRequest request) {
        Integer productCount = productStockService.modify(productId, request.getStockCount());
        return Response.success(ProductStockResponse.fromProductStock(productCount));
    }
}
