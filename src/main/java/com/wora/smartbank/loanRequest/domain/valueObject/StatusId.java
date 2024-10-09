package com.wora.smartbank.loanRequest.domain.valueObject;

import java.util.UUID;

public record StatusId(UUID value) {
    public StatusId() {
        this(UUID.randomUUID());
    }
}
