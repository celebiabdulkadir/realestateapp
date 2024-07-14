package com.abdulkadir.order.client.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentClientResponseDTO {

    private Long id;
    private Long orderId;
    private Number totalPrice;
    private String paymentDate;
}
