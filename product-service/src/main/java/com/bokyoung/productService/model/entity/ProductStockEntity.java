package com.bokyoung.productService.model.entity;

import com.bokyoung.productService.model.ProductType;
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
@Table(name = "\"product_stock\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE product_stock SET deleted_at = NOW(), sales_status = 'OFF' WHERE id = ?")
public class ProductStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType; //상품유형 (일반상품, 예약구매상품)

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

    public static ProductStockEntity of(Long productId, ProductType productType, Integer stockCount) {
        ProductStockEntity entity = new ProductStockEntity();
        entity.setProductId(productId);
        entity.setProductType(productType);
        entity.setStockCount(stockCount);
        entity.updateSalesStatus();

        return entity;
    }

    /**
     * 판매 상태
     */
    public void updateSalesStatus() {
        if(this.productType == ProductType.GENERAL) { //일반 상품일 때 초기 상태는 활성
            this.salesStatus = SalesStatus.ON;
        } else { //예약구매 상품일 때 초기 상태는 비활성
            this.salesStatus = SalesStatus.OFF;
        }
    }
}