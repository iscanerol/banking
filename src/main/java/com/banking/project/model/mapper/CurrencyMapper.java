package com.banking.project.model.mapper;

import com.banking.project.model.dto.CurrencyDto;
import com.banking.project.model.entity.CurrencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDto toCurrencyDto(CurrencyEntity source);

    CurrencyEntity toCurrencyEntity(CurrencyDto source);

    List<CurrencyDto> toCurrencyDtoList(List<CurrencyEntity> entityList);

    List<CurrencyEntity> toCurrencyEntityList(List<CurrencyDto> dtoList);
}
