package com.wora.smartbank.loanRequest.domain.valueObject;

import com.wora.smartbank.common.domain.valueObject.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Embeddable
public record CustomerDetails(

        @Enumerated(EnumType.STRING)
        @NotNull
        Title title,

        @Column(name = "customer_first_name")
        @NotBlank
        String firstName,

        @Column(name = "customer_last_name")
        @NotBlank
        String lastName,

        @Column(name = "customer_email", unique = true)
        @NotBlank @Email
        String email,

        @Column(name = "customer_phone")
        @NotBlank // todo : add phone validation
        String phone,

        @Column(name = "customer_profession")
        @NotBlank
        String profession,

        @Embedded
        @AttributeOverride(name = "value", column = @Column(name = "customer_cin"))
        @NotNull
        Cin cin,

        @Column(name = "customer_date_of_birth")
        @NotNull
        LocalDate dateOfBirth,

        @Column(name = "customer_employment_start_date")
        @NotNull
        LocalDate employmentStartDate,

        @NotNull
        Double monthlyIncome,

        @Column(name = "customer_has_existing_loans")
        @NotNull
        Boolean hasExistingLoans
) {
}
