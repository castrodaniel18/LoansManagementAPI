package com.caixa.loans.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.caixa.loans.model.LoanStatus;

public record LoanRequestResponse(Long id, String name, BigDecimal amount, String currency, String documentId,
		LoanStatus status, LocalDateTime creationDate) {
}
