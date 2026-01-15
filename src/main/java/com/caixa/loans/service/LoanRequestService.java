package com.caixa.loans.service;

import java.util.List;

import com.caixa.loans.model.LoanRequest;
import com.caixa.loans.model.LoanStatus;

public interface LoanRequestService {

	LoanRequest create(LoanRequest loanRequest);

	LoanRequest getById(Long id);

	List<LoanRequest> getAll();

	LoanRequest updateStatus(Long id, LoanStatus newStatus);
}
