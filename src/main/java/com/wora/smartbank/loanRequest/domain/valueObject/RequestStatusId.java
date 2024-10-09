package com.wora.smartbank.loanRequest.domain.valueObject;

import java.util.UUID;

public record RequestStatusId(UUID value) {
    public RequestStatusId() {
        this(UUID.randomUUID());
    }
}
