package com.wora.smartbank.common.domain.exception;

import com.wora.smartbank.common.domain.valueObject.ValidationErrors;

import java.util.Map;

public class InvalidRequestException extends RuntimeException {
    private final ValidationErrors validationErrors;

    public InvalidRequestException(ValidationErrors validationErrors) {
        super(validationErrors.message());
        this.validationErrors = validationErrors;
        validationErrors.errors().forEach((k, v) -> System.err.println(k + "  ->  " + v));
    }

    public Map<String, String> errors() {
        return validationErrors.errors();
    }

    public String message () {
        return validationErrors.message();
    }
}
