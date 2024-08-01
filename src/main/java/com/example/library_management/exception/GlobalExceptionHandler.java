package com.example.library_management.exception;

import com.example.library_management.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
		ApiResponse<Object> response = new ApiResponse<>(
				false,
				ex.getMessage(),
				HttpStatus.NOT_FOUND.value(),
				null
		);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		ApiResponse<Object> apiResponse = new ApiResponse<>(
				false,
				ex.getMessage(),
				HttpStatus.BAD_REQUEST.value(),
				null
		);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<List<String>>> handleConstraintViolationException(ConstraintViolationException ex) {
		List<String> errors = ex.getConstraintViolations().stream()
				.map(violation -> violation.getMessage())
				.collect(Collectors.toList());
		
		ApiResponse<List<String>> response = new ApiResponse<>(
				false,
				"Validation failed",
				HttpStatus.BAD_REQUEST.value(),
				errors
		);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		ApiResponse<Object> response = new ApiResponse<>(
				false,
				ex.getMessage(),
				HttpStatus.METHOD_NOT_ALLOWED.value(),
				null
		);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<ApiResponse<Object>> handleTransactionSystemException(TransactionSystemException ex) {
		Throwable cause = ex.getMostSpecificCause();
		String message = "Transaction system exception occurred";
		
		if (cause instanceof ConstraintViolationException constraintViolationException) {
			List<String> errors = constraintViolationException.getConstraintViolations().stream()
					.map(violation -> violation.getMessage())
					.collect(Collectors.toList());
			
			message = "Validation failed: " + String.join(", ", errors);
		} else {
			message += ": " + cause.getMessage();
		}
		
		ApiResponse<Object> response = new ApiResponse<>(
				false,
				message,
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				null
		);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistException(UserAlreadyExistException ex) {
		ApiResponse<Object> response = new ApiResponse<>(
				false,
				ex.getMessage(),
				HttpStatus.CONFLICT.value(),
				null
		);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
		ApiResponse<Object> response = new ApiResponse<>(
				false,
				"An unexpected error occurred",
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage()
		);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
