package com.caixa.loans.exception;

public class NotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1299354368448553107L;

	public NotFoundException(String message) {
		super(message);
	}
}