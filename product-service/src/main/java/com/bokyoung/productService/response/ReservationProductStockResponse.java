package com.bokyoung.productService.response;

import com.bokyoung.productService.model.ReservationProductStock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationProductStockResponse {

    private Integer stockCount;

    private String salesStatus;

    public static ReservationProductStockResponse fromReservationProductStock(ReservationProductStock reservationProductStock) {
        return new ReservationProductStockResponse(
                reservationProductStock.getStockCount(),
                reservationProductStock.getSalesStatus().getSalesStatusText()
        );
    }

}
