package com.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email Id already taken/exists")
public class EmailAlreadyTakenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
