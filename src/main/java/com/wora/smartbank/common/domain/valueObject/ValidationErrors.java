package com.wora.smartbank.common.domain.valueObject;

import jakarta.validation.ConstraintViolation;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record ValidationErrors<T>(String message, Map<String, String> errors) {

    public ValidationErrors(String message, Set<ConstraintViolation<T>> constraintViolations) {
        this(
                message,
                constraintViolations.stream()
                        .collect(Collectors.toMap(
                                c -> c.getPropertyPath().toString(),
                                ConstraintViolation::getMessage
                        ))
        );
    }
}