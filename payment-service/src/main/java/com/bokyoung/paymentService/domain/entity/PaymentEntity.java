package com.bokyoung.paymentService.domain.entity;

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
@Table(name = "\"payment\"", indexes = {
        @Index(columnList = "id")
})
@EqualsAndHashCode(of = "id")
@Entity
@SQLDelete(sql = "UPDATE payment SET deleted_at = NOW() WHERE id = ?")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    private Timestamp createdAt;

    private Timestamp deletedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    public static PaymentEntity of(Long orderId, Long userId) {
        PaymentEntity entity = new PaymentEntity();
        entity.setOrderId(orderId);
        entity.setUserId(userId);

        return entity;
    }
}
