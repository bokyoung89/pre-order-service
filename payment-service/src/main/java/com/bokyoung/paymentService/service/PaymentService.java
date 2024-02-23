package com.bokyoung.paymentService.service;

import com.bokyoung.paymentService.client.OrderFeignClient;
import com.bokyoung.paymentService.exception.ErrorCode;
import com.bokyoung.paymentService.exception.PreOrderServiceException;
import com.bokyoung.paymentService.model.entity.PaymentEntity;
import com.bokyoung.paymentService.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;

    @Transactional
    public void createPayment(Long orderId, Long userId) {

        double probability = Math.random();

        // 80%만 성공 (20%는 결제 실패 이탈)
        if (probability <= 0.8) {
            //결제 성공
            paymentRepository.save(PaymentEntity.of(orderId, userId));
            //주문상태 결제완료로 변경
            orderFeignClient.updateStatusPayment(orderId);
        }
        // 20% 고객 이탈로 결제 취소
        // 주문상태를 주문취소로 변경 및 재고 반영
            orderFeignClient.updateStatusCancelAddStock(orderId);
            throw new PreOrderServiceException(ErrorCode.PAYMENT_FAILED);
    }
}
