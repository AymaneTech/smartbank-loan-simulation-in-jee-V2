package com.wora.smartbank.loanRequest.application.dto.response;

import java.time.LocalDateTime;

public record HistoryItem(
        StatusResponse status,
        String description,
        LocalDateTime createdAt
) {
}
