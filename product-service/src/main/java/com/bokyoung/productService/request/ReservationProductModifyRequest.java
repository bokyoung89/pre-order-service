package com.bokyoung.productService.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationProductModifyRequest {

    private String name;

    private String content;

    private int price;

    public ReservationProductModifyRequest() {
    }
}
