package com.bokyoung.orderService.controller;

import com.bokyoung.orderService.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service/orders")
@RequiredArgsConstructor
public class OrderController {

    @GetMapping
    public Response<Void> api_test() {
        return Response.success();
    }
}