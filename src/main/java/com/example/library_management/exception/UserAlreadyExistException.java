package com.example.library_management.exception;

public class UserAlreadyExistException extends RuntimeException {
	public UserAlreadyExistException(String message) {
		super(message);
	}
}
