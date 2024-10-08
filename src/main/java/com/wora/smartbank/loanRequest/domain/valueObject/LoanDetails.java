package com.wora.smartbank.loanRequest.domain.valueObject;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Embeddable
public record LoanDetails(
        @NotBlank String project,
        @NotNull @Positive Double amount,
        @NotNull @Positive Double duration,
        @NotNull @Positive Double monthly
) {
    public LoanDetails(LoanDetails loanDetails, Double monthly) {
        this(loanDetails.project(), loanDetails.amount(), loanDetails.duration(), monthly);
    }
}