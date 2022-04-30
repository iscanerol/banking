package com.banking.project.exception.advice;

import com.banking.project.exception.OperationResultException;
import com.banking.project.model.dto.OperationResult;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handle(ConstraintViolationException ex) {
        final Map<String, String> errors = new HashMap<>();

        if (!CollectionUtils.isEmpty(ex.getConstraintViolations())) {
            ex.getConstraintViolations()
                    .forEach(error -> {
                        String fieldName = null;
                        if (error.getPropertyPath() != null && ((PathImpl) error.getPropertyPath()).getLeafNode() != null) {
                            fieldName = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
                        }
                        String errorMessage = error.getMessageTemplate();
                        errors.put(fieldName != null ? fieldName : "Key: ", errorMessage);
                    });
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationResultException.class)
    public ResponseEntity<OperationResult> handle(OperationResultException ex) {
        return new ResponseEntity<>(ex.getOperationResult(), HttpStatus.BAD_REQUEST);
    }
}
