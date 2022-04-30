package com.banking.project.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private String description;
    private Integer accountNumber;
    private String accountType;
    private BigDecimal accountBalance;
    private String accountBalanceType;
    @ManyToOne
    private PersonEntity person;
}
