package lef.server.exception;

public class BadRequestException extends Exception {

	public BadRequestException() {
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}
}
