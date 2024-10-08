package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;

public interface LoanCalculationValidationService {
    LoanDetails calculate(LoanDetails loanDetails);
}
