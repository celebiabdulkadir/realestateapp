package com.abdulkadir.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "UserId cannot be null")
    private Long userId;

    @NotNull(message = "PackageId cannot be null")
    private Long packageId;

    @NotNull(message = "Package quantity cannot be null")
    private Number packageQuantity;

    @NotNull(message = "Package price cannot be null")
    private Number packagePrice;

    @NotNull(message = "Package advert quantity cannot be null")
    private Number packageAdvertQuantity;



    // Assuming orderDate is set at the time of order creation and not provided by the client
    // Assuming orderStatus and paymentStatus are set to default values at the time of order creation and not provided by the client
}