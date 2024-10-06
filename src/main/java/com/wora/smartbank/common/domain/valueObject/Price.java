package com.wora.smartbank.common.domain.valueObject;

import jakarta.persistence.Embeddable;

import java.util.Currency;

@Embeddable
public record Price(Double amount, Currency currency) {
}