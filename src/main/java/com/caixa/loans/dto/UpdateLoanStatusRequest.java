package com.caixa.loans.dto;

import com.caixa.loans.model.LoanStatus;

public record UpdateLoanStatusRequest(LoanStatus status) {
}