package com.banking.project.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cards")
@Getter
@Setter

public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardNumber;
    private String pinCode;
    @ManyToOne
    private AccountEntity account;

}
