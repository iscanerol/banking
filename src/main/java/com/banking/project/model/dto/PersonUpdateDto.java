package com.banking.project.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateDto {
    private String email;
    private String name;
    private String surname;
    private String telNo;
}
