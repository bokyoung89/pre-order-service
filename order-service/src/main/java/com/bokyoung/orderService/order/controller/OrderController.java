package com.bokyoung.orderService.order.controller;

import com.bokyoung.orderService.order.dto.request.OrderCreateRequest;
import com.bokyoung.orderService.order.dto.response.OrderResponse;
import com.bokyoung.orderService.order.dto.response.Response;
import com.bokyoung.orderService.order.domain.model.Order;
import com.bokyoung.orderService.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 결제 진입 : 구매하기 클릭 시 주문 생성
     */
    @PostMapping
    public Response<OrderResponse> create(@RequestBody OrderCreateRequest request,
                                 @RequestHeader(name = "principalId") Long principalId) {
        Order order = orderService.create(request.getProductId(), request.getQuantity(), request.getAddress(), principalId);
        return Response.success(OrderResponse.fromOrder(order));
    }

    /**
     * 주문 취소
     */
    @DeleteMapping("/{orderId}")
    public Response<Void> cancel(@PathVariable(name="orderId") Long orderId,
                                 @RequestHeader(name = "principalId") Long principalId) {
        orderService.cancel(orderId, principalId);
        return Response.success();
    }

    /**
     * 주문 단건(상세) 조회
     */
    @GetMapping("/{orderId}")
    public Response<OrderResponse> loadOrder(@PathVariable(name="orderId") Long orderId) {
        Order order = orderService.loadOrder(orderId);
        return Response.success(OrderResponse.fromOrder(order));
    }

    /**
     * 주문 리스트 조회
     */
    @GetMapping
    public Response<Page<OrderResponse>> loadOrders(Pageable pageable,
                                                   @RequestHeader(name = "principalId") Long principalId) {
        return Response.success(orderService.loadOrders(pageable, principalId).map(OrderResponse::fromOrder));
    }
}