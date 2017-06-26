package com.diff.json;

/**
 * This exception is thrown when the data informed to
 * BinaryJSON is not a valid json content. 
 * 
 * @author Elias
 *
 */
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
