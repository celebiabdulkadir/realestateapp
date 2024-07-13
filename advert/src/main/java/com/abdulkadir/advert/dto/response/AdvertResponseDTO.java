package com.abdulkadir.advert.dto.response;

import com.abdulkadir.advert.model.enums.AdvertStatus;
import com.abdulkadir.advert.model.enums.AdvertType;
import com.abdulkadir.advert.model.enums.Heating;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String address;
    private Number price;
    private Number area;
    private Number room;
    private Number floor;
    private Number totalFloor;
    private Heating heating;
    private Boolean balcony;
    private Boolean elevator;
    private Boolean fromHomeOwner;
    private Boolean fromAgency;
    private Boolean furnished;
    private Boolean credit;
    private Boolean swap;
    private AdvertType advertType;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private AdvertStatus advertStatus;
}