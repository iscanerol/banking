package com.banking.project.controller;

import com.banking.project.model.dto.CurrencyDto;
import com.banking.project.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/currency")
@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/create")
    public ResponseEntity<List<CurrencyDto>> createCurrency(@RequestBody List<CurrencyDto> currencyDtoList) {
        return ResponseEntity.ok(currencyService.createCurrencyList(currencyDtoList));
    }
}
