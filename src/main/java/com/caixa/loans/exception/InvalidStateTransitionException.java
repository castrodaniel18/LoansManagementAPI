package com.caixa.loans.exception;

public class InvalidStateTransitionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6398572277972287638L;

	public InvalidStateTransitionException(String message) {
		super(message);
	}
}