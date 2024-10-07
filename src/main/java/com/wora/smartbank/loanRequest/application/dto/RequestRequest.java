package com.wora.smartbank.loanRequest.application.dto;

import com.wora.smartbank.common.domain.valueObject.Price;
import com.wora.smartbank.loanRequest.domain.valueObject.Title;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RequestRequest(
        @NotBlank String project,
        @NotNull @Positive Double amount,
        @NotNull @Positive Double duration,
        @NotNull @Positive Double monthly,
        @NotNull Title title,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        // todo: add phone regex pattern validation
        @NotBlank String phone,
        @NotBlank String profession,
        // todo: add cin pattern validation
        @NotBlank String cin,
        @NotNull LocalDate dateOfBirth,
        @NotNull LocalDate employmentStartDate,
        @NotNull Double monthlyIncome,
        @NotNull Boolean hasExistingLoans
) {
}