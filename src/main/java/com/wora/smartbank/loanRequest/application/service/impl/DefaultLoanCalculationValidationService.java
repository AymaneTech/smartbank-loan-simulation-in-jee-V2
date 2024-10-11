package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.loanRequest.application.service.LoanCalculationValidationService;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultLoanCalculationValidationService implements LoanCalculationValidationService {
    private final static Double TAX_RATE = 12.0;
    private final static Double MONTHLY_RATE = TAX_RATE / 12.0 / 100.0;

    @Override
    public LoanDetails calculate(LoanDetails loanDetails) {
        Double calculatedMonthlyPayment = (loanDetails.amount() * MONTHLY_RATE) / (1 - Math.pow(1 + MONTHLY_RATE, -loanDetails.duration()));

        return new LoanDetails(loanDetails, calculatedMonthlyPayment);
    }
}
