package com.banking.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageType {
    VALIDATE_SSN_CHARACTERS("SSN 8 karakterli olmalidir"),
    VALIDATE_TELNO_CHARACTERS("Telefon Numarası 10 haneli olmalidir"),
    VALIDATE_NULL("Bu Alanlar Bos Birakilamaz"),
    VALIDATE_MISSING_CHARACTERS("eksik karakter girdiniz"),
    VALIDATE_NOT_FOUND("Boyle bir kayit bulunamadi"),
    VALIDATE_ADDRESS("Sistemde en az bir address bulunmalidir"),
    VALIDATE_SAME_TYPE("Ayni hesap turunden tekrar olusturulamaz"),
    VALIDATE_PERSON_ACCOUNT("Bu hesap bu kisiye ait degil"),
    VALIDATE_NOT_FOUND_ACCOUNT_TYPE("Bu kisinin #p hesabi yok"),
    VALIDATE_AMOUNT("Bakiye yetersiz");


    private String errorMessage;
}
