package com.caixa.loans.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caixa.loans.exception.NotFoundException;
import com.caixa.loans.model.LoanRequest;
import com.caixa.loans.model.LoanStatus;
import com.caixa.loans.repository.LoanRequestRepository;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

	private final LoanRequestRepository repository;

	public LoanRequestServiceImpl(LoanRequestRepository repository) {
		this.repository = repository;
	}

	// Creacion de una nueva solicitud
	@Override
	public LoanRequest create(LoanRequest loanRequest) {
		return repository.save(loanRequest);
	}

	// Recupera una solicitud por id
	@Override
	public LoanRequest getById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new NotFoundException("LoanRequest with id " + id + " not found"));
	}

	// Recupera el listado completo de solicitudes
	@Override
	public List<LoanRequest> getAll() {
		return repository.findAll();
	}

	// Actualiza el estado de solicitud si es posible, si no, lanza excepcion
	@Override
	public LoanRequest updateStatus(Long id, LoanStatus newStatus) {
		LoanRequest loan = getById(id);
		loan.changeStatus(newStatus);

		return repository.save(loan);
	}
}
