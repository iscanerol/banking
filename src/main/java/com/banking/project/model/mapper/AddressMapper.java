package com.banking.project.model.mapper;

import com.banking.project.model.dto.AddressDto;
import com.banking.project.model.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto toAddressDto(AddressEntity source);

    AddressEntity toAddressEntity(AddressDto source);

    List<AddressDto> toAddressDtoList(List<AddressEntity> entityList);

    List<AddressEntity> toAddressEntityList(List<AddressDto> dtoList);


}