package com.banking.project.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "cardOperation")
@Getter
@Setter

public class CardOperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardId;
    private BigDecimal amount;
    private String currency;
    @ManyToOne
    private PersonEntity person;
    @ManyToOne
    private AccountEntity account;
    @ManyToOne
    private CardEntity cards;

}
