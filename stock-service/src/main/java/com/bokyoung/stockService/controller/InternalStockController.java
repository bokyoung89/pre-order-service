package com.bokyoung.stockService.controller;

import com.bokyoung.stockService.model.ProductType;
import com.bokyoung.stockService.response.Response;
import com.bokyoung.stockService.service.InternalProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/stocks")
public class InternalStockController {

    private final InternalProductStockService internalProductStockService;

    @PostMapping
    public Response<Void> createStock(@RequestParam Long productId, ProductType productType, Integer stockCount) {
        internalProductStockService.createStock(productId, productType, stockCount);
        return Response.success();
    }

    @GetMapping
    public ResponseEntity<Integer> getStock(@RequestParam Long productId) {
        Integer stockCount = internalProductStockService.getStock(productId);
        return ResponseEntity.ok(stockCount);
    }

    @PutMapping("/reduce")
    public Response<Void> reduceStock(@RequestParam Long productId,
                                      @RequestParam Integer quantity) {
        internalProductStockService.reduceStock(productId, quantity);
        return Response.success();
    }

    @PutMapping("/increase")
    public Response<Void> increaseStock(@RequestParam Long productId,
                                        @RequestParam Integer quantity) {
        internalProductStockService.increaseStock(productId, quantity);
        return Response.success();
    }

    @PutMapping("/modify")
    public Response<Void> modifyStock(@RequestParam Long productId,
                                        @RequestParam Integer quantity) {
        internalProductStockService.modifyStock(productId, quantity);
        return Response.success();
    }

    @DeleteMapping
    public Response<Void> deleteStock(@RequestParam Long productId) {
        internalProductStockService.deleteStock(productId);
        return Response.success();
    }
}
