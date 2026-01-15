package com.caixa.loans.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.caixa.loans.model.LoanRequest;

@Repository
public class InMemoryLoanRequestRepository implements LoanRequestRepository {

	// Uso de ConcurrentHashMap para permitir guardados simultaneos, HashMap es secuencial
	private final Map<Long, LoanRequest> storage = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(1);

	@Override
	public LoanRequest save(LoanRequest loanRequest) {
		LoanRequest savedLoanRequest = loanRequest;

		// Nuevas solicitudes
		if (loanRequest.getId() == null) {
			long id = sequence.getAndIncrement();
			savedLoanRequest = new LoanRequest(id, loanRequest.getName(), loanRequest.getAmount(),
					loanRequest.getCurrency(), loanRequest.getDocumentId(), loanRequest.getCreationDate(),
					loanRequest.getStatus());
		}

		storage.put(savedLoanRequest.getId(), savedLoanRequest);
		return savedLoanRequest;
	}

	@Override
	public Optional<LoanRequest> findById(Long id) {
		return Optional.ofNullable(storage.get(id));
	}

	@Override
	public List<LoanRequest> findAll() {
		return new ArrayList<>(storage.values());
	}
}
