package com.javaegitimleri.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class OwnerNotFoundException extends RuntimeException {

	public OwnerNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OwnerNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public OwnerNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OwnerNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public OwnerNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
