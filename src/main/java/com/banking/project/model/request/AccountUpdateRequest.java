package com.banking.project.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequest {
    private Long accountId;
    private String description;
}
