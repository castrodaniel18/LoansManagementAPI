package com.caixa.loans.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest request) {
		ApiError error = new ApiError(ErrorCodes.LOAN_REQUEST_NOT_FOUND, ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(InvalidStateTransitionException.class)
	public ResponseEntity<ApiError> handleInvalidStateTransition(InvalidStateTransitionException ex,
			HttpServletRequest request) {
		ApiError error = new ApiError(ErrorCodes.INVALID_STATE_TRANSITION, ex.getMessage());

		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
		ApiError error = new ApiError(ErrorCodes.BAD_REQUEST, ex.getMessage());

		return ResponseEntity.badRequest().body(error);
	}

	
	//Lanzada cuando algún parámetro es invalido
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining(", "));

		ApiError error = new ApiError(ErrorCodes.VALIDATION_ERROR, message);

		return ResponseEntity.badRequest().body(error);
	}

	//Errores internos
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
		ApiError error = new ApiError(ErrorCodes.INTERNAL_ERROR, "Unexpected error occurred");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
