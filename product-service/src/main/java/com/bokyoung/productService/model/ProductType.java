package com.bokyoung.productService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductType {

    GENERAL_PURCHASE("일반"),
    RESERVATION_PURCHASE("예약 구매")
    ;

    private final String ProductTypeText;

}
