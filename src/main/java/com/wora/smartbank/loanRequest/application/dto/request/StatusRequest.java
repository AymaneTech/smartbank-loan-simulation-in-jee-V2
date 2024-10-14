package com.wora.smartbank.loanRequest.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record StatusRequest(@NotBlank String name) {
}
