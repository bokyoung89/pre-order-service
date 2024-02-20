package com.bokyoung.productService.model.entity;

import com.bokyoung.productService.model.SalesStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.sql.Timestamp;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"reservation_product_stock\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE reservation_product_stock SET deleted_at = NOW() WHERE id = ?")
public class ReservationProductStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer stockCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SalesStatus salesStatus;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp deletedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void modifiedAt() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }

    public static ReservationProductStockEntity of(Long productId, Integer stockCount) {
        ReservationProductStockEntity entity = new ReservationProductStockEntity();
        entity.setProductId(productId);
        entity.setStockCount(stockCount);
        entity.updateSalesStatus();

        return entity;
    }

    /**
     * 판매 상태
     */
    public void updateSalesStatus() {
        this.salesStatus = SalesStatus.OFF;
    }
}
