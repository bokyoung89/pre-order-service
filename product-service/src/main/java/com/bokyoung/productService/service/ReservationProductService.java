package com.bokyoung.productService.service;

import com.bokyoung.productService.exception.ErrorCode;
import com.bokyoung.productService.exception.PreOrderServiceException;
import com.bokyoung.productService.model.ReservationProduct;
import com.bokyoung.productService.model.entity.ReservationProductEntity;
import com.bokyoung.productService.model.entity.ReservationProductStockEntity;
import com.bokyoung.productService.repository.ReservationProductRepository;
import com.bokyoung.productService.repository.ReservationProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReservationProductService {

    private final ReservationProductRepository reservationProductRepository;
    private final ReservationProductStockRepository reservationProductStockRepository;
    @Transactional
    public void create(String name, Long userId, String content, int price, Integer stocksCount) {
        //상품 등록
        ReservationProductEntity reservationProductEntity = reservationProductRepository.save(ReservationProductEntity.of(name, userId, content, price));

        //재고는 0보다 커야 한다.
        ReservationProductStockEntity ReservationproductStockEntity = ReservationProductStockEntity.of(reservationProductEntity.getId(), stocksCount);
        if(ReservationproductStockEntity.getStockCount() == 0) {
            throw new PreOrderServiceException(ErrorCode.STOCK_COUNT_IS_ZERO, String.format("StockCount is zero"));
        }
        //재고 등록
        reservationProductStockRepository.save(ReservationproductStockEntity);
    }

    @Transactional
    public ReservationProduct modify(String name, String content, int price, Long userId, Long productId) {
        ReservationProductEntity reservationProductEntity = getReservationProductEntityOrException(productId);

        if (reservationProductEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        if (name != null) {
            reservationProductEntity.setName(name);
        }
        if (content != null) {
            reservationProductEntity.setContent(content);
        }
        reservationProductEntity.setPrice(price);

        return ReservationProduct.fromEntity(reservationProductRepository.save(reservationProductEntity));
    }

    @Transactional
    public void delete(Long userId, Long productId) {
        ReservationProductEntity reservationProductEntity = getReservationProductEntityOrException(productId);
        ReservationProductStockEntity reservationProductStockEntity = getReservationProductStockEntityOrException(productId);

        if (reservationProductEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        reservationProductRepository.delete(reservationProductEntity);
        reservationProductStockRepository.delete(reservationProductStockEntity);
    }

    public Page<ReservationProduct> list(Pageable pageable) {
        return reservationProductRepository.findAll(pageable).map(ReservationProduct::fromEntity);
    }

    public ReservationProduct details(Long productId) {
        ReservationProductEntity reservationProductEntity = getReservationProductEntityOrException (productId);
        return ReservationProduct.fromEntity(reservationProductEntity);
    }

    // product exist
    private ReservationProductEntity getReservationProductEntityOrException(Long productId) {
        return reservationProductRepository.findById(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.PRODUCT_NOT_FOUND, String.format("%s not founded", productId)));
    }

    // stock exist
    private ReservationProductStockEntity getReservationProductStockEntityOrException(Long productId) {
        return reservationProductStockRepository.findById(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productId)));
    }
}
