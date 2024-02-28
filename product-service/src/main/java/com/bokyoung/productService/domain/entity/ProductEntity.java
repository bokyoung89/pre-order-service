package com.bokyoung.productService.domain.entity;

import com.bokyoung.productService.domain.model.ProductType;
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
@Table(name = "\"product\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE id = ?")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; //등록한 사용자 : 관리자 권한

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType; //상품유형 (일반상품, 예약구매상품)

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

    public static ProductEntity of(String name, Long userId, String content, int price, ProductType productType) {
        ProductEntity entity = new ProductEntity();
        entity.setName(name);
        entity.setUserId(userId);
        entity.setContent(content);
        entity.setPrice(price);
        entity.setProductType(productType);

        return entity;
    }
}
