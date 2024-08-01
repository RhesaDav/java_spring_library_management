package com.example.library_management.exception;

public class DataAlreadyExistException extends RuntimeException {
	public DataAlreadyExistException(String message) {
		super(message);
	}
}
