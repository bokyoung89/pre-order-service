package com.bokyoung.productService.controller;

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

    @GetMapping("/{productId}/stocks")
    public Response<ProductStockResponse> getStockCount(@PathVariable(name = "productId") Long productId) {
        Integer productCount = productStockService.getStockCount(productId);
        return Response.success(ProductStockResponse.fromProductStock(productCount));
    }

    @PutMapping("/{productId}/stocks")
    public Response<ProductStockResponse> modify(@PathVariable(name = "productId") Long productId,
                                                 @RequestBody ProductStockModifyRequest request) {
        Integer productCount = productStockService.modify(productId, request.getStockCount());
        return Response.success(ProductStockResponse.fromProductStock(productCount));
    }
}
