package com.banking.project.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConvertCurrencyRequest {
    private String convertedCurrencyType;
    private BigDecimal convertedAmount;
    private Long personId;
}
