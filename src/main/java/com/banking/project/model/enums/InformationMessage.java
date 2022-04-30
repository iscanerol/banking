package com.banking.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InformationMessage {
    AMOUNT_RECEIVED("#p miktarında alış işlemi gerçekleşti"),
    AMOUNT_SELLING("#p hesabinizdan Tl hesabiniza #p liralik alis gerceklestirilmistir"),
    WITHDRAWAL_AMOUNT("#p hesabinizdan #p liralik para cektiniz"),
    ADDING_AMOUNT("#p hesabiniza #p liralik para eklediniz");

    private String errorMessage;
}
