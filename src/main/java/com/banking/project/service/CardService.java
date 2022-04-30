package com.banking.project.service;

import com.banking.project.model.dto.CardDto;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.dto.PersonDto;
import com.banking.project.model.entity.CardEntity;
import com.banking.project.model.entity.PersonEntity;
import com.banking.project.model.enums.OperationResultCode;
import com.banking.project.model.mapper.CardMapper;
import com.banking.project.model.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class CardService {
    private  final AccountService accountService;
    private final PersonService personService;

    public OperationResult createCard(CardDto cardDto){
        CardEntity cardEntity = CardMapper.INSTANCE.toCardEntity(cardDto);
        PersonDto personDto = personService.findPerson(cardDto.getAccount().getPerson().getEmail());
        PersonEntity personEntity = PersonMapper.INSTANCE.toPersonEntity(personDto);
        cardEntity.setAccount(null);

        return OperationResult.newInstance(OperationResultCode.SUCCESS);
    }


}
