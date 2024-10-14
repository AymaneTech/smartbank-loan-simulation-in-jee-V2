package com.wora.smartbank.loanRequest.application.dto.response;

import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.Title;

import java.time.LocalDate;

public record RequestResponse(
        RequestId id,
        String project,
        Double amount,
        Double duration,
        Double monthly,
        Title title,
        String firstName,
        String lastName,
        String email,
        String phone,
        String profession,
        String cin,
        LocalDate dateOfBirth,
        LocalDate employmentStartDate,
        Double monthlyIncome,
        Boolean hasExistingLoans
) {
}
