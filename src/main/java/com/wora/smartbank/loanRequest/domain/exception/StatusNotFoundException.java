package com.wora.smartbank.loanRequest.domain.exception;

import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;

public class StatusNotFoundException extends RuntimeException {
    private final StatusId id;

    public StatusNotFoundException(StatusId id) {
        super("status with id " + id.value() + " not found");
        this.id = id;
    }
}
