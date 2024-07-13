package com.abdulkadir.order.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageRequestDTO {
    private String name;
    private Number price;
    private String description;
    private Number duration;
    private Number advertQuantity;
}