package com.abdulkadir.order.model;

import com.abdulkadir.order.enums.OrderStatus;
import com.abdulkadir.order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "/order/")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="package_id")
    private Long packageId;

    @Column(name="package_quantity")
    private Number packageQuantity;

    @Column(name="package_price")
    private Number packagePrice;

    @Column(name="package_advert_quantity")
    private Number packageAdvertQuantity;

    @Column(name="advert_count")
    private int advertCount;

    @Column(name="total_price")
    private Number totalPrice;

    @Column(name="payment_amount")
    private Number paymentAmount;

    @Column(name="payment_id")
    private Long paymentId;

    @Column(name="payment_date")
    private LocalDateTime paymentDate;

    @Column(name="expiry_date")
    private LocalDateTime expiryDate ;

    @CreatedDate
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.WAITING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.WAITING;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.orderDate = now;
        this.updateDate = now;
        calculateAdvertCount();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = LocalDateTime.now();
        calculateAdvertCount();
    }

    private void calculateAdvertCount() {
        if (packageQuantity != null && packageAdvertQuantity != null) {
            this.advertCount = packageQuantity.intValue() * packageAdvertQuantity.intValue();
        }
    }
}