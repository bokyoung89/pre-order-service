package com.bokyoung.controller;

import com.bokyoung.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-service/products")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping
    public Response<Void> api_test() {
       return Response.success();
    }
}
