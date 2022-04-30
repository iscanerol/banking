package com.banking.project.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CurrencyDto {
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date currencyDate;
    private BigDecimal amount;
}
