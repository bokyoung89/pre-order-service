package com.bokyoung.stockService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),

    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "Stock not founded"),

    STOCK_COUNT_IS_ZERO(HttpStatus.BAD_REQUEST, "StockCount is zero"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
    ;

    private HttpStatus status;
    private String message;
}
