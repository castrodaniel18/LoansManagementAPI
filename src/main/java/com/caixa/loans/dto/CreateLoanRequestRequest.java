package com.caixa.loans.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLoanRequestRequest(@NotBlank String name,
		@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal amount, @NotBlank String currency,
		@NotBlank String documentId) {
}