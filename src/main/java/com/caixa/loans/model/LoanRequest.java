package com.caixa.loans.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.caixa.loans.exception.InvalidStateTransitionException;

import lombok.Data;

@Data
public class LoanRequest {

	private final Long id;
	private final String name;
	private final BigDecimal amount;
	private final String currency;
	private final String documentId;
	private final LocalDateTime creationDate;

	// Enumerado de estados de solicitud
	private LoanStatus status;

	public LoanRequest(Long id, String name, BigDecimal amount, String currency, String documentId,
			LocalDateTime creationDate, LoanStatus status) {
		this.id = id;
		this.name = Objects.requireNonNull(name, "name is required");
		this.amount = Objects.requireNonNull(amount, "amount is required");
		if (this.amount.signum() <= 0) {
		    throw new IllegalArgumentException("amount must be > 0");
		}
		this.currency = Objects.requireNonNull(currency, "currency is required");
		this.documentId = Objects.requireNonNull(documentId, "documentId is required");
		this.creationDate = Objects.requireNonNull(creationDate, "createdAt is required");
		this.status = Objects.requireNonNull(status, "status is required");
	}
	
	// El id se genera secuencialmente antes del guardado
	public LoanRequest(String name, BigDecimal amount, String currency, String documentId, LocalDateTime creationDate,
			LoanStatus status) {
		this(null, name, amount, currency, documentId, creationDate, status);
	}

	public void changeStatus(LoanStatus newStatus) {
		if (!this.status.canChangeState(newStatus)) {
			throw new InvalidStateTransitionException("Cannot transition from " + this.status + " to " + newStatus);
		}
		this.status = newStatus;
	}
}
