package com.banking.project.model.mapper;

import com.banking.project.model.dto.PersonDto;
import com.banking.project.model.dto.PersonUpdateDto;
import com.banking.project.model.entity.PersonEntity;
import com.banking.project.model.request.PersonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto toPersonDto(PersonEntity source);

    PersonEntity toPersonEntity(PersonDto source);

    PersonEntity toPersonEntity(PersonRequest source);

    PersonRequest toPersonRequest(PersonEntity source);

    PersonEntity toPersonEntity(PersonUpdateDto source);

    PersonUpdateDto toPersonUpdateDto(PersonEntity source);

    List<PersonDto> toPersonDtoList(List<PersonEntity> entityList);

    List<PersonEntity> toPersonEntityList(List<PersonDto> dtoList);


}