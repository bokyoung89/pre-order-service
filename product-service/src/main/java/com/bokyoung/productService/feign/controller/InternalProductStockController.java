package com.bokyoung.productService.feign.controller;

import com.bokyoung.productService.feign.service.InternalProductStockService;
import com.bokyoung.productService.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal")
public class InternalProductStockController {

    private final InternalProductStockService internalProductStockService;

    @GetMapping("/stocks/count")
    public ResponseEntity<Integer> getProductStockCount(@RequestParam Long productId) {
        Integer stockCount = internalProductStockService.getProductStockCount(productId);
        return ResponseEntity.ok(stockCount);
    }

    @PostMapping("/stocks/remove")
    public Response<Void> removeStockCount(@RequestParam Long productId,
                                           @RequestParam Integer quantity) {
        internalProductStockService.removeStockCount(productId, quantity);
        return Response.success();
    }

    @PostMapping("/stocks/add")
    public Response<Void> addStockCount(@RequestParam Long productId,
                                        @RequestParam Integer quantity) {
        internalProductStockService.addStockCount(productId, quantity);
        return Response.success();
    }
}
