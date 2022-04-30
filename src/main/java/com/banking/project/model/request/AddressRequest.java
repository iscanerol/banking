package com.banking.project.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private Long id;
    private String street;
    private String district;
    private String city;
}
