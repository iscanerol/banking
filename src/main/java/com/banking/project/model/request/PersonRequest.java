package com.banking.project.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter

public class PersonRequest {
    //@NotBlank ?

    @NotNull(message = " name  may not be null")
    private String name;
    @NotNull(message = " surname  may not be null")
    private String surname;
    @NotNull(message = " email  may not be null")
    private String email;
    @NotNull(message = " password  may not be null")
    @Size(min = 7, max = 20)
    private String password;
    @NotNull(message = " ssn  may not be null")
    private String ssn;
    @NotNull(message = " telNo  may not be null")
    private String telNo;
}