package com.banking.project.service;

import com.banking.project.exception.OperationResultException;
import com.banking.project.model.dto.AddressDto;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.dto.PersonDto;
import com.banking.project.model.entity.AddressEntity;
import com.banking.project.model.entity.PersonEntity;
import com.banking.project.model.enums.ErrorMessageType;
import com.banking.project.model.enums.OperationResultCode;
import com.banking.project.model.mapper.AddressMapper;
import com.banking.project.model.mapper.PersonMapper;
import com.banking.project.model.request.AddressRequest;
import com.banking.project.model.request.SingleAddressRequest;
import com.banking.project.repository.AddressRepository;
import com.banking.project.util.BankingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final PersonService personService;

    @Transactional
    public OperationResult createAddress(AddressDto addressDto) {
        PersonDto person = personService.findPerson(addressDto.getPerson().getEmail());
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setDistrict(addressDto.getDistrict());
        addressEntity.setCity(addressDto.getCity());
        PersonEntity personEntity = PersonMapper.INSTANCE.toPersonEntity(person);
        addressEntity.setPerson(personEntity);

        addressRepository.save(addressEntity);

        return OperationResult.successResult();

    }

    @Transactional(readOnly = true)
    public List<AddressDto> findAddress(String email) {
//        List<AddressEntity> byPersonEmail = addressRepository.findByPersonEmail(email);

        PersonDto person = personService.findPerson(email);
        List<AddressEntity> addressEntityList = addressRepository.findByPersonId(person.getId());
        return AddressMapper.INSTANCE.toAddressDtoList(addressEntityList);
    }

    @Transactional
    public OperationResult removeAllAddress(String email) {
        PersonDto person = personService.findPerson(email);
        List<AddressEntity> addressEntityList = addressRepository.findByPersonId(person.getId());

        addressRepository.deleteAll(addressEntityList);

        return OperationResult.successResult();
    }

    @Transactional
    public OperationResult updateAddress(AddressRequest addressRequest) {
        AddressEntity addressEntity = addressRepository.findById(addressRequest.getId()).orElseThrow(() -> OperationResultException.builder()
                .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                        ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage())).build());

        if (addressRequest.getCity() != null) {
            addressEntity.setCity(addressRequest.getCity());
        }
        if (addressRequest.getStreet() != null) {
            addressEntity.setStreet(addressRequest.getStreet());
        }
        if (addressRequest.getDistrict() != null) {
            addressEntity.setDistrict(addressRequest.getDistrict());
        }

        addressRepository.save(addressEntity);

        return OperationResult.successResult();
    }

    @Transactional
    public AddressDto updateSingleAddress(SingleAddressRequest singleAddressRequest) {
        AddressEntity addressEntity = addressRepository.findById(singleAddressRequest.getId())
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR, ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage()))
                        .build());
        if (singleAddressRequest.getStreet() != null) {
            addressEntity.setStreet(singleAddressRequest.getStreet());
        }
        if (singleAddressRequest.getDistrict() != null) {
            addressEntity.setDistrict(singleAddressRequest.getDistrict());
        }
        if (singleAddressRequest.getCity() != null) {
            addressEntity.setDistrict(singleAddressRequest.getCity());
        }
        addressRepository.save(addressEntity);
        return AddressMapper.INSTANCE.toAddressDto(addressEntity);
    }

    @Transactional
    public List<AddressDto> singleRemoveAddress(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage()))
                        .build());
        List<AddressEntity> addressEntityList = addressRepository.findByPersonId(addressEntity.getPerson().getId());

        if (addressEntityList.size() > 1) {
            addressRepository.delete(addressEntity);
        }
        BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                ErrorMessageType.VALIDATE_ADDRESS.getErrorMessage()));


        addressEntityList.removeIf(addressValue -> addressValue.getId().equals(addressEntity.getId()));

        return AddressMapper.INSTANCE.toAddressDtoList(addressEntityList);

    }
}