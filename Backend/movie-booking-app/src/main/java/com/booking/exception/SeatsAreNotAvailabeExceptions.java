package com.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "All seats are already booked")
public class SeatsAreNotAvailabeExceptions extends Exception {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
