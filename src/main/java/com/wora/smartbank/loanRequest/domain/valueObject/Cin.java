package com.wora.smartbank.loanRequest.domain.valueObject;

import jakarta.persistence.Embeddable;

@Embeddable
public record Cin(String value) {
}
