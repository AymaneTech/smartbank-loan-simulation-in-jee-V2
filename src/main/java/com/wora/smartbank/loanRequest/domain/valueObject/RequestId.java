package com.wora.smartbank.loanRequest.domain.valueObject;

import java.util.UUID;

public record RequestId(UUID value) {
    public RequestId() {
        this(UUID.randomUUID());
    }
}
