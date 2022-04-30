package com.banking.project.controller;

import com.banking.project.model.dto.AccountDto;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.request.AccountUpdateRequest;
import com.banking.project.model.request.ConvertCurrencyRequest;
import com.banking.project.model.request.MoneyUpdateRequest;
import com.banking.project.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<OperationResult> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }
    @PostMapping("/upDate")
    public ResponseEntity<OperationResult> upDateAccount(@RequestBody AccountUpdateRequest accountUpdateRequest) {
        return ResponseEntity.ok(accountService.updateAccount(accountUpdateRequest));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<OperationResult> deleteAllAccount(@RequestParam String email) {
        return ResponseEntity.ok(accountService.removeAllAccount(email));
    }

    @DeleteMapping("/deleteSingle")
    public ResponseEntity<OperationResult> deleteSingleAccount(@RequestParam Long id) {
        return ResponseEntity.ok(accountService.singleRemoveAccount(id));
    }

    @PostMapping("/convertCurrency")
    public ResponseEntity<OperationResult> convertCurrency(@RequestBody ConvertCurrencyRequest convertCurrencyRequest) {
        return ResponseEntity.ok(accountService.takeForeignCurrency(convertCurrencyRequest));
    }

    @PostMapping("/convertTL")
    public ResponseEntity<OperationResult> convertTL(@RequestBody ConvertCurrencyRequest convertCurrencyRequest) {
        return ResponseEntity.ok(accountService.takeTlCurrency(convertCurrencyRequest));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<OperationResult> withdrawal(@RequestBody MoneyUpdateRequest moneyUpdateRequest) {
        return ResponseEntity.ok(accountService.withdrawalFromTlAccount(moneyUpdateRequest));
    }

    @PostMapping("/adding")
    public ResponseEntity<OperationResult> addingMoney(@RequestBody MoneyUpdateRequest moneyUpdateRequest) {
        return ResponseEntity.ok(accountService.addingMoney(moneyUpdateRequest));
    }
}
