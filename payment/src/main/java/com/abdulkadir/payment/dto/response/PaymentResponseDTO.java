package com.abdulkadir.payment.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long id;
    private Long orderId;
    private Number totalPrice;
    private String paymentDate;
}
