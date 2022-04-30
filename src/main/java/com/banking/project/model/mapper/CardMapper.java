package com.banking.project.model.mapper;

import com.banking.project.model.dto.CardDto;
import com.banking.project.model.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")

public interface CardMapper {


    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    CardDto toCardDto(CardEntity source);

    CardEntity toCardEntity(CardDto source);

    List<CardDto> toCardDtoList(List<CardEntity> entityList);

    List<CardEntity> toCardEntityList(List<CardDto> dtoList);
}
