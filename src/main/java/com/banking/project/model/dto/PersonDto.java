package com.banking.project.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String ssn;
    private String telNo;
}
