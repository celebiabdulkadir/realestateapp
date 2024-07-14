package com.abdulkadir.order.client.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentClientRequestDTO {

    @NotNull(message = "OrderId cannot be null")
    private Long orderId;

    @NotNull(message = "Total price cannot be null")
    private Number totalPrice;

}
