package com.cruz.exception;

public class NullorInvalidPathVariableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NullorInvalidPathVariableException() {
	}

	public NullorInvalidPathVariableException(String message) {
		super(message);
	}
}
