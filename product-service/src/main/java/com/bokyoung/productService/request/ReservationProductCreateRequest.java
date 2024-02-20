package com.bokyoung.productService.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationProductCreateRequest {
    //상품 및 재고
    private String name;

    private String content;

    private int price;

    private Integer stockCount;

    public ReservationProductCreateRequest() {
    }
}
