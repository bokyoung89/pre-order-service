package com.bokyoung.stockService.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductType {

    GENERAL("일반"),
    RESERVATION("예약")
    ;

    private final String ProductTypeText;
}
