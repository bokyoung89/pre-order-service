package com.bokyoung.productService.application.controller;

import com.bokyoung.productService.domain.model.Product;
import com.bokyoung.productService.dto.request.ProductModifyRequest;
import com.bokyoung.productService.dto.response.ProductResponse;
import com.bokyoung.productService.application.service.ProductService;
import com.bokyoung.productService.dto.request.ProductCreateRequest;
import com.bokyoung.productService.dto.response.Response;
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
    public Response<Page<ProductResponse>> list(Pageable pageable) {
        return Response.success(productService.list(pageable).map(ProductResponse::fromProduct));
    }

    @GetMapping("/{productId}")
    public Response<ProductResponse> details(@PathVariable(name = "productId") Long productId) {
        Product product = productService.details(productId);
        return Response.success(ProductResponse.fromProduct(product));
    }
}
