package com.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Credentials are wrong")
public class CredentialsAreWrongException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
