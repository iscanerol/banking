package com.banking.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
    TL("TL"),
    EUR("EURO"),
    XAU("ALTIN");

    private String accountType;
}
