package com.bokyoung.productService.product.controller;

import com.bokyoung.productService.product.domain.model.Product;
import com.bokyoung.productService.product.dto.request.ProductModifyRequest;
import com.bokyoung.productService.product.dto.response.ProductResponse;
import com.bokyoung.productService.product.application.ProductService;
import com.bokyoung.productService.product.dto.request.ProductCreateRequest;
import com.bokyoung.productService.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Response<Void> create(@RequestBody ProductCreateRequest request,
                                 @RequestHeader(name = "principalId") Long principalId) {
        productService.create(request.getName(), principalId, request.getContent(), request.getPrice(),  request.getProductType(), request.getStockCount());
        return Response.success();
    }

    @PutMapping("/{productId}")
    public Response<ProductResponse> modify(@PathVariable(name = "productId") Long productId,
                                            @RequestBody ProductModifyRequest request,
                                            @RequestHeader(name = "principalId") Long principalId) {
        Product product = productService.modify(request.getName(), request.getContent(), request.getPrice(), request.getProductType(), request.getStockCount(), productId, principalId);
        return Response.success(ProductResponse.fromProduct(product));
    }

    @DeleteMapping("/{productId}")
    public Response<Void> delete(@PathVariable(name = "productId") Long productId,
                                 @RequestHeader(name = "principalId") Long principalId) {
        productService.delete(principalId, productId);
        return Response.success();
    }

    @GetMapping
    public Response<Page<ProductResponse>> loadProducts(Pageable pageable) {
        return Response.success(productService.loadProducts(pageable).map(ProductResponse::fromProduct));
    }

    @GetMapping("/{productId}")
    public Response<ProductResponse> loadProduct(@PathVariable(name = "productId") Long productId) {
        Product product = productService.loadProduct(productId);
        return Response.success(ProductResponse.fromProduct(product));
    }
}
