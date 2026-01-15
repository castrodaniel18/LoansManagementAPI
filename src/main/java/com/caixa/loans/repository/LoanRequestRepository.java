package com.caixa.loans.repository;

import java.util.List;
import java.util.Optional;

import com.caixa.loans.model.LoanRequest;

public interface LoanRequestRepository {
	LoanRequest save(LoanRequest loanRequest);

	Optional<LoanRequest> findById(Long id);

	List<LoanRequest> findAll();
}
