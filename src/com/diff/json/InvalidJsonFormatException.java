package com.diff.json;

public class InvalidJsonFormatException extends Exception {

	public InvalidJsonFormatException() {
	}

	public InvalidJsonFormatException(String message) {
		super(message);
	}

	public InvalidJsonFormatException(Throwable cause) {
		super(cause);
	}

}
