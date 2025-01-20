package com.javaegitimleri.petclinic.exception;

public class VetNotFoundException extends RuntimeException {

	public VetNotFoundException() {
		super();
	}

	public VetNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VetNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VetNotFoundException(String message) {
		super(message);
	}

	public VetNotFoundException(Throwable cause) {
		super(cause);
	}

}
