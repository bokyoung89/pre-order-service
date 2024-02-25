package com.bokyoung.productService.controller;

import com.bokyoung.productService.model.ReservationProductStock;
import com.bokyoung.productService.request.ReservationProductStockModifyRequest;
import com.bokyoung.productService.response.ReservationProductStockResponse;
import com.bokyoung.productService.response.Response;
import com.bokyoung.productService.service.ReservationProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-service/reservationProducts")
@RequiredArgsConstructor
public class ReservationProductStockController {

    private final ReservationProductStockService reservationProductStockService;

    @GetMapping("/stocks/{stockId}")
    public Response<ReservationProductStockResponse> getStockCount(@PathVariable(name = "stockId") Long stockId) {
        ReservationProductStock reservationProductStock = reservationProductStockService.getStockCount(stockId);
        return Response.success(ReservationProductStockResponse.fromReservationProductStock(reservationProductStock));
    }

    @PutMapping("/stocks/{stockId}")
    public Response<ReservationProductStockResponse> modify(@PathVariable(name = "stockId") Long stockId,
                                                            @RequestBody ReservationProductStockModifyRequest request) {
        ReservationProductStock reservationProductStock = reservationProductStockService.modify(stockId, request.getStockCount());
        return Response.success(ReservationProductStockResponse.fromReservationProductStock(reservationProductStock));
    }
}
