package com.bokyoung.orderService.internal.service;

import com.bokyoung.orderService.infra.feign.StockFeignClient;
import com.bokyoung.orderService.global.exception.ErrorCode;
import com.bokyoung.orderService.global.exception.PreOrderServiceException;
import com.bokyoung.orderService.domain.model.Order;
import com.bokyoung.orderService.domain.model.OrderStatus;
import com.bokyoung.orderService.domain.entity.OrderEntity;
import com.bokyoung.orderService.dao.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalOrderService {

    private final OrderRepository orderRepository;
    private final StockFeignClient productFeignClient;

    @Transactional
    public void updateStatusPayment(Long orderId) {
        OrderEntity orderEntity = getOrderEntityOrException(orderId);
        //결제 완료 처리
        orderEntity.setOrderStatus(OrderStatus.PAYMENT);
        Order.fromEntity(orderRepository.save(orderEntity));
    }

    @Transactional
    public void updateStatusCancelAddStock(Long orderId) {
        OrderEntity orderEntity = getOrderEntityOrException(orderId);
        //취소수량만큼 재고 증가(product-service 메서드 feign 호출)
        productFeignClient.increaseStock(orderEntity.getProductId(), orderEntity.getQuantity());
        //주문 취소 처리
        orderRepository.delete(orderEntity);
    }

    // order exist
    private OrderEntity getOrderEntityOrException(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.ORDER_NOT_FOUND, String.format("%s not founded", orderId)));
    }
}
