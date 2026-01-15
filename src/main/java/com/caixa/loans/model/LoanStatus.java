package com.caixa.loans.model;

import java.util.EnumSet;
import java.util.Set;

public enum LoanStatus {
	PENDING, APPROVED, REJECTED, CANCELED;

	//Logica de estados permitidos
	public Set<LoanStatus> allowedStates() {
		return switch (this) {
		case PENDING -> EnumSet.of(APPROVED, REJECTED);
		case APPROVED -> EnumSet.of(CANCELED);
		case REJECTED, CANCELED -> EnumSet.noneOf(LoanStatus.class);
		};
	}

	//Devuelve true si se puede pasar al estado solicitado
	public boolean canChangeState(LoanStatus nextState) {
		if (nextState == null)
			return false;
		return allowedStates().contains(nextState);
	}
}
