package com.banking.project.repository;

import com.banking.project.model.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Long> {
    CurrencyEntity findByFromCurrencyAndCurrencyDate(String currency, Date date);
}
