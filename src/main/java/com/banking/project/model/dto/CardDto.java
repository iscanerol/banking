package com.banking.project.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDto {

    private Long cardNumber;
    private String pinCode;
    private AccountDto account;
}
