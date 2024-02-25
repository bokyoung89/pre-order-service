package com.bokyoung.productService.response;

import com.bokyoung.productService.model.ReservationProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationProductResponse {

    private Long id;

    private String name;

    private String content;

    private int price;

    public static ReservationProductResponse fromReservationProduct(ReservationProduct reservationProduct) {
        return new ReservationProductResponse(
                reservationProduct.getId(),
                reservationProduct.getName(),
                reservationProduct.getContent(),
                reservationProduct.getPrice()
        );
    }
}
