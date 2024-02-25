package com.bokyoung.productService.model;

import com.bokyoung.productService.model.entity.ReservationProductStockEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationProductStock {

    private Integer stockCount;

    private SalesStatus salesStatus;

    public static ReservationProductStock fromEntity(ReservationProductStockEntity entity) {
        return new ReservationProductStock(
                entity.getStockCount(),
                entity.getSalesStatus()
        );
    }
}
