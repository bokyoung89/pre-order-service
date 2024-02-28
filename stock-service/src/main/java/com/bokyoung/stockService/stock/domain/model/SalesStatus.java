package com.bokyoung.stockService.stock.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SalesStatus {

    ON("활성"),
    OFF("비활성"),
    SOLD_OUT("품절")
    ;

    private final String SalesStatusText;
}
