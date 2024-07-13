package com.abdulkadir.advert.model.converters;

import com.abdulkadir.advert.model.enums.AdvertType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AdvertTypeConverter implements AttributeConverter<AdvertType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AdvertType attribute) {
        if (attribute == null) {
            return null;
        }
        switch (attribute) {
            case SALE:
                return 1;
            case RENT:
                return 2;
            default:
                throw new IllegalArgumentException("Unknown " + attribute);
        }
    }

    @Override
    public AdvertType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case 1:
                return AdvertType.SALE;
            case 2:
                return AdvertType.RENT;
            default:
                throw new IllegalArgumentException("Unknown " + dbData);
        }
    }
}