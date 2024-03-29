package com.bokyoung.orderService.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not founded"),
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "Stock not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),

    CHECK_QUANTITY(HttpStatus.BAD_REQUEST, "Quantity cannot be greater than stock")
    ;

    private HttpStatus status;
    private String message;
}
