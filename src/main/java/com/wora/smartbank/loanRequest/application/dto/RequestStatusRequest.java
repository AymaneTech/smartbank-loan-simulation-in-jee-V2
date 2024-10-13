package com.wora.smartbank.loanRequest.application.dto;

import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import jakarta.validation.constraints.NotNull;

public record RequestStatusRequest(
        @NotNull String description,
        @NotNull Status status,
        @NotNull Request request
) {
}
