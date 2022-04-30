package com.banking.project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Table(name = "currency")
@Entity
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private Date currencyDate;
    private BigDecimal amount;
}
