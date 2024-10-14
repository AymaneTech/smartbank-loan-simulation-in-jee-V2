package com.wora.smartbank.loanRequest.application.dto;

import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;

public record StatusResponse(
        StatusId id,
        String name
) {
}
