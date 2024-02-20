package com.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Movie Id Exception raised- handled by custom exception")
public class MovieIdAlreadyExistsExceptions extends Exception{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

}
