package com.example.Porter.Exception;

public class InvalidTokenException extends RuntimeException{
	private static final long serialVersionUID = 4L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
