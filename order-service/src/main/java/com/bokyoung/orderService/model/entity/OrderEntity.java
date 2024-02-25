package com.bokyoung.orderService.model.entity;

import com.bokyoung.orderService.model.OrderStatus;
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
@Table(name = "\"orders\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW(), order_status = 'CANCEL' WHERE id = ?")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long userId; //등록한 사용자 : 관리자 권한

    @Column(nullable = false)
    private int quantity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
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

    public static OrderEntity of(Long productId, Long userId, int quantity, String address) {
        OrderEntity entity = new OrderEntity();
        entity.setProductId(productId);
        entity.setUserId(userId);
        entity.setQuantity(quantity);
        entity.setAddress(address);
        entity.updateOrderStatus();

        return entity;
    }

    /**
     * 주문상태 초기 셋팅
     */
    public void updateOrderStatus() {
            this.orderStatus = OrderStatus.ORDER;
    }
}