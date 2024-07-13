package com.abdulkadir.order.dto.response;

import com.abdulkadir.order.enums.OrderStatus;
import com.abdulkadir.order.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private Long packageId;
    private Number packageQuantity;
    private Number packagePrice;
    private Number packageAdvertQuantity;
    private int advertCount;
    private Number totalPrice;
    private Number paymentAmount;
    private Long paymentId;
    private LocalDateTime paymentDate;
    private LocalDateTime expiryDate;
    private LocalDateTime orderDate;
    private LocalDateTime updateDate;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
}