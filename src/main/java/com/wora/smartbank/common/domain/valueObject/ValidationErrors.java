package com.wora.smartbank.common.domain.valueObject;

import java.util.Map;

public record ValidationErrors(String message, Map<String, String> errors) {
}