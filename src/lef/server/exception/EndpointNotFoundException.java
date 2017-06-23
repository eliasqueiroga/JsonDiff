package lef.server.exception;

public class EndpointNotFoundException extends Exception {

	public EndpointNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public EndpointNotFoundException(String message) {
		super(message);
	}

	public EndpointNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
