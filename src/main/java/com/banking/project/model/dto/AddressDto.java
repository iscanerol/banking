package com.banking.project.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private Long id;
    private String street;
    private String district;
    private String city;
    private PersonDto person;
}
