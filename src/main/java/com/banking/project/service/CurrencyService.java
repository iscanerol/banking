package com.banking.project.service;

import com.banking.project.model.dto.CurrencyDto;
import com.banking.project.model.entity.CurrencyEntity;
import com.banking.project.model.mapper.CurrencyMapper;
import com.banking.project.repository.CurrencyRepository;
import com.banking.project.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Transactional
    public List<CurrencyDto> createCurrencyList(List<CurrencyDto> currencyDtoList) {
        List<CurrencyEntity> currencyEntityList = currencyRepository.saveAll(CurrencyMapper.INSTANCE
                .toCurrencyEntityList(currencyDtoList));

        return CurrencyMapper.INSTANCE.toCurrencyDtoList(currencyEntityList);
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrentCurrencyDto(String currency) {
        CurrencyEntity currencyEntity = currencyRepository.findByFromCurrencyAndCurrencyDate(currency, DateUtil.dateFormat());
        return CurrencyMapper.INSTANCE.toCurrencyDto(currencyEntity);
    }
}
