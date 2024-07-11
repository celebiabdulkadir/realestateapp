package com.abdulkadir.advert.dto.request;

import com.abdulkadir.advert.model.enums.AdvertStatus;
import com.abdulkadir.advert.model.enums.AdvertType;
import com.abdulkadir.advert.model.enums.Heating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequestDTO {

    @NotNull(message = "UserId cannot be null")
    private Number userId;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Address cannot be empty")
    @Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters")
    private String address;

    @NotNull(message = "Price cannot be null")
    private Number price;

    @NotNull(message = "Area cannot be null")
    private Number area;

    @NotNull(message = "Room cannot be null")
    private Number room;

    @NotNull(message = "Floor cannot be null")
    private Number floor;

    @NotNull(message = "Total floor cannot be null")
    private Number totalFloor;


    @NotNull(message = "Heating cannot be null")
    private Heating heating;

    @NotNull(message = "Balcony cannot be null")
    private Boolean balcony;

    @NotNull(message = "Elevator cannot be null")
    private Boolean elevator;

    @NotNull(message = "From Home Owner cannot be null")
    private Boolean fromHomeOwner;

    @NotNull(message = "From Agency cannot be null")
    private Boolean fromAgency;

    @NotNull(message = "Furnished cannot be null")
    private Boolean furnished;

    @NotNull(message = "Credit cannot be null")
    private Boolean credit;

    @NotNull(message = "Swap cannot be null")
    private Boolean swap;

    @NotNull(message = "Advert Type cannot be null")
    private AdvertType advertType; // For Sale or For Rent

    private AdvertStatus advertStatus = AdvertStatus.IN_REVIEW;
}