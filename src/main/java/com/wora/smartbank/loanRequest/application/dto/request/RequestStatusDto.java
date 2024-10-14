package com.wora.smartbank.loanRequest.application.dto.request;

import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import jakarta.validation.constraints.NotNull;

public record RequestStatusDto(
        @NotNull String description,
        @NotNull StatusId statusId,
        @NotNull RequestId requestId
) {
}
