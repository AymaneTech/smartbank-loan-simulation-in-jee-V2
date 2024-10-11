package com.wora.smartbank.loanRequest.application.dto;

import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import jakarta.validation.constraints.NotNull;

public record RequestStatusRequest(
        @NotNull StatusId statusId,
        @NotNull RequestId requestId
) {
}
