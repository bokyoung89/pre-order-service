package com.bokyoung.orderService.exception;

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
    ;

    private HttpStatus status;
    private String message;
}
