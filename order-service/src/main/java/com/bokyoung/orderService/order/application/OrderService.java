package com.bokyoung.orderService.order.application;

import com.bokyoung.orderService.infra.StockFeignClient;
import com.bokyoung.orderService.global.exception.ErrorCode;
import com.bokyoung.orderService.global.exception.PreOrderServiceException;
import com.bokyoung.orderService.order.domain.model.Order;
import com.bokyoung.orderService.order.domain.entity.OrderEntity;
import com.bokyoung.orderService.order.dao.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StockFeignClient productFeignClient;

    @Transactional
    public Order create(Long productId, int quantity, String address, Long userId) {
        // 재고 확인
        Integer stockCount = productFeignClient.getStock(productId);
        if(stockCount == 0) {
            throw new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("재고가 없습니다."));
        }

        // 남은 재고 안에서만 주문 가능 (주문수량은 재고보다 클 수 없다.)
        if(stockCount < quantity) {
            throw new PreOrderServiceException(ErrorCode.CHECK_QUANTITY);
        }

        // 주문 생성
        OrderEntity orderEntity = orderRepository.save(OrderEntity.of(productId, userId, quantity, address));

        // TODO : 주문수량만큼 재고 감소(product-service 메서드 feign 호출)
        productFeignClient.reduceStock(productId, quantity);
        return Order.fromEntity(orderEntity);
    }

    @Transactional
    public void cancel(Long orderId, Long userId) {

        //주문 존재 여부 체크
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.ORDER_NOT_FOUND, String.format("order is not found", orderId)));

        //주문자가 일치하는지 체크
        if(!orderEntity.getUserId().equals(userId)) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission", userId));
        }

        //주문 취소
        orderRepository.delete(orderEntity);

        // 취소수량만큼 재고 증가(product-service 메서드 feign 호출)
        productFeignClient.increaseStock(orderEntity.getProductId(), orderEntity.getQuantity());
    }

    public Order loadOrder(Long orderId) {

        //주문 존재 여부 체크
        return orderRepository.findById(orderId).map(Order::fromEntity).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.ORDER_NOT_FOUND, String.format("order is not found")));
    }

    public Page<Order> loadOrders(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId).map(Order::fromEntity);
    }
}
