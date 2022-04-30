package com.banking.project.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDto {
    private Long accountId;
    private String description;
    private Integer accountNumber;
    private String accountType;
    private BigDecimal accountBalance;
    private String accountBalanceType;
    private PersonDto person;
}
