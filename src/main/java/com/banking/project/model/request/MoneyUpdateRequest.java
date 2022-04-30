package com.banking.project.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MoneyUpdateRequest {
    private String updateCurrencyType;
    private BigDecimal updateAmount;
    private Long personId;
}

