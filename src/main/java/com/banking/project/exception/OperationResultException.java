package com.banking.project.exception;

import com.banking.project.model.dto.OperationResult;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationResultException extends RuntimeException {
    private OperationResult operationResult;
}
