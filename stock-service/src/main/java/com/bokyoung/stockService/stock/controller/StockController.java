package com.bokyoung.stockService.stock.controller;

import com.bokyoung.stockService.stock.dto.request.ProductStockModifyRequest;
import com.bokyoung.stockService.stock.dto.response.ProductStockResponse;
import com.bokyoung.stockService.stock.dto.response.Response;
import com.bokyoung.stockService.stock.application.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
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
