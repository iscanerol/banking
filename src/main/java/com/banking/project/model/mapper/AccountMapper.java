package com.banking.project.model.mapper;

import com.banking.project.model.dto.AccountDto;
import com.banking.project.model.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toAccountDto(AccountEntity source);

    AccountEntity toAccountEntity(AccountDto source);

    List<AccountDto> toAccountDtoList(List<AccountEntity> entityList);

    List<AccountEntity> toAccountEntityList(List<AccountDto> dtoList);

}
