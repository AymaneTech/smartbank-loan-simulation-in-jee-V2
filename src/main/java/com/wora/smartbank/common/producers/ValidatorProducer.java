package com.wora.smartbank.common.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidatorProducer {
    private Validator validator;

    @Produces
    @ApplicationScoped
    public Validator createValidator() {
        if (validator == null) {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return validator;
    }
}
