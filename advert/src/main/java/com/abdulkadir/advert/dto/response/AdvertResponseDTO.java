package com.abdulkadir.advert.dto.response;

import com.abdulkadir.advert.model.enums.AdvertStatus;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertResponseDTO {
    private Long id;
    private Number userId;
    private String title;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private AdvertStatus advertStatus;
}