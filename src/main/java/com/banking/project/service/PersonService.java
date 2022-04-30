package com.banking.project.service;

import com.banking.project.exception.OperationResultException;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.dto.PersonDto;
import com.banking.project.model.dto.PersonUpdateDto;
import com.banking.project.model.entity.PersonEntity;
import com.banking.project.model.enums.ErrorMessageType;
import com.banking.project.model.enums.OperationResultCode;
import com.banking.project.model.mapper.PersonMapper;
import com.banking.project.model.request.PersonRequest;
import com.banking.project.repository.PersonRepository;
import com.banking.project.util.BankingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public OperationResult createPerson(PersonDto personDto) {
        this.validatePersonRequest(personDto);
        PersonEntity personEntity = PersonMapper.INSTANCE.toPersonEntity(personDto);
//        Yukarıdaki mapper aşağıdaki comment içerisindeki işlemleri yapıyor.
//        PersonEntity personEntity = new PersonEntity();
//        personEntity.setName(personDto.getName());
//        personEntity.setSurname(personDto.getSurname());
//        personEntity.setEmail(personDto.getEmail());
//        personEntity.setPassword(personDto.getPassword());
//        personEntity.setSsn(personDto.getSsn());
//        personEntity.setTelNo(personDto.getTelNo());

        personRepository.save(personEntity);

        return OperationResult.successResult();
    }

    @Transactional
    public OperationResult validationCreatePerson(PersonRequest personRequest) {

        PersonEntity personEntity = PersonMapper.INSTANCE.toPersonEntity(personRequest);

       /* PersonEntity personEntity = new PersonEntity();
        personEntity.setName(personRequest.getName());
        personEntity.setSurname(personRequest.getSurname());
        personEntity.setEmail(personRequest.getEmail());
        personEntity.setPassword(personRequest.getPassword());
        personEntity.setTelNo(personRequest.getTelNo());
        personEntity.setSsn(personRequest.getSsn());*/

        personRepository.save(personEntity);
        return OperationResult.successResult();
    }

    @Transactional
    public OperationResult updatePerson(PersonUpdateDto personUpdateDto) {
        this.validatePersonUpdateRequest(personUpdateDto);
        PersonEntity personEntity = personRepository.findByEmail(personUpdateDto.getEmail());
        if (personEntity == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage()));
        }

        if (personUpdateDto.getName() != null) {
            personEntity.setName(personUpdateDto.getName());
        }
        if (personUpdateDto.getSurname() != null) {
            personEntity.setSurname(personUpdateDto.getSurname());
        }
        if (personUpdateDto.getTelNo() != null) {
            personEntity.setTelNo(personUpdateDto.getTelNo());
        }

        personRepository.save(personEntity);
        return OperationResult.successResult();
    }

    @Transactional
    public OperationResult removePerson(Long id) {
        PersonEntity personEntity = personRepository.getById(id);
        if (personEntity == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage()));
        }

        personRepository.delete(personEntity);
        return OperationResult.successResult();
    }

    @Transactional(readOnly = true)
    public PersonDto findPerson(String email) {
        if (email == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }
        PersonEntity personEntity = personRepository.findByEmail(email);
        if (personEntity == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage()));
        }

        PersonDto personDto = PersonMapper.INSTANCE.toPersonDto(personEntity);
        return personDto;
    }

    @Transactional(readOnly = true)
    public List<PersonDto> personRequestList() {
        List<PersonEntity> personEntityList = personRepository.findAll();
        List<PersonDto> personDto = PersonMapper.INSTANCE.toPersonDtoList(personEntityList);
        return personDto;
    }

    private void validatePersonRequest(PersonDto personDto) {
        if (personDto.getName() == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }
        if (personDto.getSurname() == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }
        if (personDto.getSsn() == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }
        if (personDto.getEmail() == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }
        if (personDto.getPassword() == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }
        if (personDto.getTelNo() == null) {
            {
                BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                        ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
            }


        }

        if (personDto.getSsn().trim().length() != 8) {
            BankingUtil.throwException(
                    OperationResult.newInstance(OperationResultCode.ERROR,
                            ErrorMessageType.VALIDATE_SSN_CHARACTERS.getErrorMessage()));
        }

        if (personDto.getTelNo().trim().length() != 10) {
            BankingUtil.throwException(
                    OperationResult.newInstance(OperationResultCode.ERROR,
                            ErrorMessageType.VALIDATE_TELNO_CHARACTERS.getErrorMessage()));

        }

        if (!personDto.getEmail().trim().endsWith(".com") || !personDto.getEmail().trim().contains("@")) {
            BankingUtil.throwException(
                    OperationResult.newInstance(OperationResultCode.ERROR,
                            ErrorMessageType.VALIDATE_MISSING_CHARACTERS.getErrorMessage()));

        }
    }

    private void validatePersonUpdateRequest(PersonUpdateDto personUpdateDto) {
        if (personUpdateDto.getEmail() == null) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR, ErrorMessageType.VALIDATE_NULL.getErrorMessage()));
        }

        if (!personUpdateDto.getEmail().trim().endsWith(".com") &&
                !personUpdateDto.getEmail().trim().contains("@")) {
            BankingUtil.throwException(
                    OperationResult.newInstance(OperationResultCode.ERROR,
                            ErrorMessageType.VALIDATE_MISSING_CHARACTERS.getErrorMessage())
            );

        }

        if (personUpdateDto.getTelNo() != null && personUpdateDto.getTelNo().trim().length() != 10) {
            BankingUtil.throwException(
                    OperationResult.newInstance(OperationResultCode.ERROR,
                            ErrorMessageType.VALIDATE_TELNO_CHARACTERS.getErrorMessage()));
        }
    }
}