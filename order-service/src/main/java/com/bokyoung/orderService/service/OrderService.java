package com.bokyoung.orderService.service;

import com.bokyoung.orderService.exception.ErrorCode;
import com.bokyoung.orderService.exception.PreOrderServiceException;
import com.bokyoung.orderService.model.Order;
import com.bokyoung.orderService.model.entity.OrderEntity;
import com.bokyoung.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(Long productId, int quantity, String address, Long userId) {
        // TODO : 재고 확인(product-service 메서드 feign 호출)

        // TODO : 남은 재고 안에서만 주문 가능
        // 주문 생성
        orderRepository.save(OrderEntity.of(productId, userId, quantity, address));

        // TODO : 주문수량만큼 재고 감소(product-service 메서드 feign 호출)
    }

    @Transactional
    public void cancelOrder(Long orderId, Long userId) {

        //주문 존재 여부 체크
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.ORDER_NOT_FOUND, String.format("order is not found", orderId)));

        //주문자가 일치하는지 체크
        if(!orderEntity.getUserId().equals(userId)) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission", userId));
        }

        orderRepository.delete(orderEntity);

        // TODO : 취소수량만큼 재고 증가(product-service 메서드 feign 호출)
    }

    public Order OrderDetail(Long orderId) {

        //주문 존재 여부 체크
        return orderRepository.findById(orderId).map(Order::fromOrder).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.ORDER_NOT_FOUND, String.format("order is not found")));
    }

    public Page<Order> OrderList(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId).map(Order::fromOrder);
    }
}
