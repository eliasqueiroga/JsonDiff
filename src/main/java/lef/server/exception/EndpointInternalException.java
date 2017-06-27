package lef.server.exception;

public class EndpointInternalException extends Exception {

	public EndpointInternalException() {
	}

	public EndpointInternalException(String message) {
		super(message);
	}

	public EndpointInternalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
