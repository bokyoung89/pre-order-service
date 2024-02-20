package com.bokyoung.productService.controller;

import com.bokyoung.productService.model.ReservationProduct;
import com.bokyoung.productService.request.ReservationProductCreateRequest;
import com.bokyoung.productService.request.ReservationProductModifyRequest;
import com.bokyoung.productService.response.ReservationProductResponse;
import com.bokyoung.productService.response.Response;
import com.bokyoung.productService.service.ReservationProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-service/reservationProducts")
@RequiredArgsConstructor
public class ReservationProductController {

    private final ReservationProductService reservationProductService;

    @PostMapping
    public Response<Void> create(@RequestBody ReservationProductCreateRequest request,
                                 @RequestHeader(name = "principalId") Long principalId) {
        reservationProductService.create(request.getName(), principalId, request.getContent(), request.getPrice(), request.getStockCount());
        return Response.success();
    }

    @PutMapping("/{productId}")
    public Response<ReservationProductResponse> modify(@PathVariable(name = "productId") Long productId,
                                                       @RequestBody ReservationProductModifyRequest request,
                                                       @RequestHeader(name = "principalId") Long principalId) {
        ReservationProduct reservationProduct = reservationProductService.modify(request.getName(), request.getContent(), request.getPrice(), principalId, productId);
        return Response.success(ReservationProductResponse.fromReservationProduct(reservationProduct));
    }

    @DeleteMapping("/{productId}")
    public Response<Void> delete(@PathVariable(name = "productId") Long productId,
                                 @RequestHeader(name = "principalId") Long principalId) {
        reservationProductService.delete(principalId, productId);
        return Response.success();
    }

    @GetMapping
    public Response<Page<ReservationProductResponse>> list(Pageable pageable) {
        return Response.success(reservationProductService.list(pageable).map(ReservationProductResponse::fromReservationProduct));
    }

    @GetMapping("/{productId}")
    public Response<ReservationProductResponse> details(@PathVariable(name = "productId") Long productId) {
        ReservationProduct reservationProduct = reservationProductService.details(productId);
        return Response.success(ReservationProductResponse.fromReservationProduct(reservationProduct));
    }
}
