package com.wora.smartbank.loanRequest.application.dto.response;

import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;

public record StatusResponse(
        StatusId id,
        String name
) {
    public StatusResponse(Status status) {
        this(status.id(), status.name());
    }
}
