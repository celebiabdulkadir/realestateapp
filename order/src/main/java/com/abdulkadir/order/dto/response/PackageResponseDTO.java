package com.abdulkadir.order.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageResponseDTO {
    private Long id;
    private String name;
    private Number price;
    private String description;
    private Number duration;
    private Number advertQuantity;
}