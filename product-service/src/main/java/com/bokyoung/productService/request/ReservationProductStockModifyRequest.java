package com.bokyoung.productService.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationProductStockModifyRequest {

    private Integer stockCount;

    public ReservationProductStockModifyRequest() {
    }
}
