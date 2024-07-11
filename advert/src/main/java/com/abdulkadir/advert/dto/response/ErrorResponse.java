package com.abdulkadir.advert.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
