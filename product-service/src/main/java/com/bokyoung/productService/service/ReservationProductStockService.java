package com.bokyoung.productService.service;

import com.bokyoung.productService.exception.ErrorCode;
import com.bokyoung.productService.exception.PreOrderServiceException;
import com.bokyoung.productService.model.ReservationProductStock;
import com.bokyoung.productService.model.SalesStatus;
import com.bokyoung.productService.model.entity.ReservationProductStockEntity;
import com.bokyoung.productService.repository.ReservationProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReservationProductStockService {

    private final ReservationProductStockRepository reservationProductStockRepository;

    @Transactional
    public ReservationProductStock getStockCount(Long stockId) {
        ReservationProductStockEntity reservationProductStockEntity = getReservationProductStockEntityOrException(stockId);
        return ReservationProductStock.fromEntity(reservationProductStockEntity);
    }

    @Transactional
    public ReservationProductStock modify(Long stockId, Integer stockCount) {
        ReservationProductStockEntity reservationProductStockEntity = getReservationProductStockEntityOrException(stockId);

        reservationProductStockEntity.setStockCount(stockCount);

        if(stockCount == 0) { //재고가 없으면
            reservationProductStockEntity.setSalesStatus(SalesStatus.SOLD_OUT);
        } else { //재고가 늘어나면
            reservationProductStockEntity.setSalesStatus(SalesStatus.ON);
        }

        return ReservationProductStock.fromEntity(reservationProductStockRepository.save(reservationProductStockEntity));
    }

    private ReservationProductStockEntity getReservationProductStockEntityOrException(Long productId) {
        return reservationProductStockRepository.findById(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productId)));
    }
}
