package com.caixa.loans.mapper;

import java.time.LocalDateTime;

import com.caixa.loans.dto.CreateLoanRequestRequest;
import com.caixa.loans.dto.LoanRequestResponse;
import com.caixa.loans.model.LoanRequest;
import com.caixa.loans.model.LoanStatus;

public class LoanRequestMapper {

	private LoanRequestMapper() {
	}

	public static LoanRequest toModel(CreateLoanRequestRequest dto) {
		return new LoanRequest(dto.name(), dto.amount(), dto.currency(), dto.documentId(),
                LocalDateTime.now(), LoanStatus.PENDING);
	}

	public static LoanRequestResponse toResponse(LoanRequest model) {
		return new LoanRequestResponse(model.getId(), model.getName(), model.getAmount(), model.getCurrency(),
				model.getDocumentId(), model.getStatus(), model.getCreationDate());
	}
}
