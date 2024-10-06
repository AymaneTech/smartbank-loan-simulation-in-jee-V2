package com.wora.smartbank.loanRequest.domain.exception;

import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;

public class RequestNotFoundException extends RuntimeException {
    private final RequestId id;

    public RequestNotFoundException(RequestId id) {
        super(" --------------- Request with id " + id.value() + " not found ----------------------");
        this.id = id;
    }
}
