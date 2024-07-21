package com.abdulkadir.advert.mapper;

import com.abdulkadir.advert.dto.request.AdvertRequestDTO;
import com.abdulkadir.advert.dto.response.AdvertResponseDTO;
import com.abdulkadir.advert.model.Advert;
import org.springframework.stereotype.Service;

@Service
public class AdvertMapper {

    public AdvertResponseDTO toAdvertResponseDTO(Advert advert) {
        return AdvertResponseDTO.builder()
                .id(advert.getId())
                .userId(advert.getUserId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .address(advert.getAddress())
                .price(advert.getPrice())
                .area(advert.getArea())
                .room(advert.getRoom())
                .floor(advert.getFloor())
                .totalFloor(advert.getTotalFloor())
                .heating(advert.getHeating())
                .balcony(advert.getBalcony())
                .elevator(advert.getElevator())
                .fromHomeOwner(advert.getFromHomeOwner())
                .fromAgency(advert.getFromAgency())
                .furnished(advert.getFurnished())
                .credit(advert.getCredit())
                .swap(advert.getSwap())
                .advertType(advert.getAdvertType())
                .createDate(advert.getCreateDate())
                .updateDate(advert.getUpdateDate())
                .advertStatus(advert.getAdvertStatus())
                .build();
    }

    public Advert toAdvert(AdvertRequestDTO advertRequestDTO) {
        return Advert.builder()
                .userId(advertRequestDTO.getUserId())
                .title(advertRequestDTO.getTitle())
                .description(advertRequestDTO.getDescription())
                .address(advertRequestDTO.getAddress())
                .price(advertRequestDTO.getPrice())
                .area(advertRequestDTO.getArea())
                .room(advertRequestDTO.getRoom())
                .floor(advertRequestDTO.getFloor())
                .totalFloor(advertRequestDTO.getTotalFloor())
                .heating(advertRequestDTO.getHeating())
                .balcony(advertRequestDTO.getBalcony())
                .elevator(advertRequestDTO.getElevator())
                .fromHomeOwner(advertRequestDTO.getFromHomeOwner())
                .fromAgency(advertRequestDTO.getFromAgency())
                .furnished(advertRequestDTO.getFurnished())
                .credit(advertRequestDTO.getCredit())
                .swap(advertRequestDTO.getSwap())
                .advertType(advertRequestDTO.getAdvertType())
                .advertStatus(advertRequestDTO.getAdvertStatus()) // Explicitly set default status
                .build();
    }

    public Advert updateAdvertFields(Advert existingAdvert, AdvertRequestDTO advertRequestDTO) {
        existingAdvert.setTitle(advertRequestDTO.getTitle());
        existingAdvert.setDescription(advertRequestDTO.getDescription());
        existingAdvert.setAddress(advertRequestDTO.getAddress());
        existingAdvert.setPrice(advertRequestDTO.getPrice());
        existingAdvert.setArea(advertRequestDTO.getArea());
        existingAdvert.setRoom(advertRequestDTO.getRoom());
        existingAdvert.setFloor(advertRequestDTO.getFloor());
        existingAdvert.setTotalFloor(advertRequestDTO.getTotalFloor());
        existingAdvert.setHeating(advertRequestDTO.getHeating());
        existingAdvert.setBalcony(advertRequestDTO.getBalcony());
        existingAdvert.setElevator(advertRequestDTO.getElevator());
        existingAdvert.setFromHomeOwner(advertRequestDTO.getFromHomeOwner());
        existingAdvert.setFromAgency(advertRequestDTO.getFromAgency());
        existingAdvert.setFurnished(advertRequestDTO.getFurnished());
        existingAdvert.setCredit(advertRequestDTO.getCredit());
        existingAdvert.setSwap(advertRequestDTO.getSwap());
        existingAdvert.setAdvertType(advertRequestDTO.getAdvertType());
        existingAdvert.setAdvertStatus(advertRequestDTO.getAdvertStatus());
        // Note: The advert status is not updated as per the original method's comment
        return existingAdvert;
    }
}