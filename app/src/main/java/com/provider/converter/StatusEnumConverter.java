package com.provider.converter;

import java.util.Arrays;
import java.util.List;

import com.provider.exception.BadRequestException;
import com.provider.model.StatusEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusEnumConverter implements AttributeConverter<StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusEnum attribute) {
        if (attribute == null) {
            return "view-only";
        }
        return attribute.toString();
    }

    @Override
    public StatusEnum convertToEntityAttribute(String dbData) {
        List<StatusEnum> statuses = Arrays.asList(StatusEnum.class.getEnumConstants());
        for (StatusEnum statusEnum : statuses) {
            if (dbData.equals(statusEnum.toString())) {
                return statusEnum;
            }
        }
        throw new BadRequestException("conversion failed");
    }
    
}
