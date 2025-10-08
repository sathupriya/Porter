package com.example.Porter.Exception;

public class AlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public AlreadyExistsException(String message) {
		super(message);
	}
}
