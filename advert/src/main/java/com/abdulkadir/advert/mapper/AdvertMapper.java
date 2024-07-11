package com.abdulkadir.advert.mapper;

import com.abdulkadir.advert.dto.request.AdvertRequestDTO;
import com.abdulkadir.advert.dto.response.AdvertResponseDTO;
import com.abdulkadir.advert.model.Advert;

import com.abdulkadir.advert.model.enums.AdvertStatus;
import org.springframework.stereotype.Service;

@Service
public class AdvertMapper {
    public AdvertResponseDTO toAdvertResponseDTO(Advert advert) {
        return AdvertResponseDTO.builder()
                .id(advert.getId())
                .userId(advert.getUserId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .createDate(advert.getCreateDate())
                .updateDate(advert.getUpdateDate())
                .advertStatus(advert.getAdvertStatus())
                .build();
    }

    public  Advert toAdvert(AdvertRequestDTO advertRequestDTO) {
        return Advert.builder()
                .title(advertRequestDTO.getTitle())
                .description(advertRequestDTO.getDescription())
                .userId(advertRequestDTO.getUserId())
                .advertStatus(advertRequestDTO.getAdvertStatus()) // Explicitly set default status
                .build();
    }

    public Advert updateAdvertFields(Advert existionAdvert, AdvertRequestDTO advertRequestDTO) {
        existionAdvert.setAdvertStatus(advertRequestDTO.getAdvertStatus());

        return existionAdvert;
    }
}
