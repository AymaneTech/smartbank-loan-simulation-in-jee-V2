package com.wora.smartbank.loanRequest.domain.valueObject;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record RequestId(UUID value) {
    public RequestId() {
        this(UUID.randomUUID());
    }
}
