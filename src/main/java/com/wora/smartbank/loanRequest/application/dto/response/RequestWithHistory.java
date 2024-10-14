package com.wora.smartbank.loanRequest.application.dto.response;

import java.util.List;

public record RequestWithHistory(
        RequestResponse request,
        List<HistoryItem> historyItems
) {
}
