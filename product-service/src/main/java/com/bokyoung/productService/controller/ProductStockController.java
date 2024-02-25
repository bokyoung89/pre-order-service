package com.bokyoung.productService.controller;

import com.bokyoung.productService.model.ProductStock;
import com.bokyoung.productService.request.ProductStockModifyRequest;
import com.bokyoung.productService.response.ProductStockResponse;
import com.bokyoung.productService.response.Response;
import com.bokyoung.productService.service.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-service/products")
@RequiredArgsConstructor
public class ProductStockController {

    private final ProductStockService productStockService;

    @GetMapping("/stocks/{stockId}")
    public Response<ProductStockResponse> getStockCount(@PathVariable(name = "stockId") Long stockId) {
        ProductStock productStock = productStockService.getStockCount(stockId);
        return Response.success(ProductStockResponse.fromProductStock(productStock));
    }

    @PutMapping("/stocks/{stockId}")
    public Response<ProductStockResponse> modify(@PathVariable(name = "stockId") Long stockId,
                                            @RequestBody ProductStockModifyRequest request) {
        ProductStock productStock = productStockService.modify(stockId, request.getStockCount());
        return Response.success(ProductStockResponse.fromProductStock(productStock));
    }
}
