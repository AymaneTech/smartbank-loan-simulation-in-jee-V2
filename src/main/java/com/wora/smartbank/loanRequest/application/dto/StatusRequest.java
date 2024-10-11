package com.wora.smartbank.loanRequest.application.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusRequest(@NotBlank String name) {
}
