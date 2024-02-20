package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.SeatsAreNotAvailabeExceptions;
import com.booking.model.Ticket;
import com.booking.model.TicketDto;
import com.booking.service.TicketService;

@RestController
@RequestMapping("/api/v1/moviebooking")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PostMapping("/ticket/book")
	public ResponseEntity<?> bookTicket(@RequestBody TicketDto ticketDto) throws SeatsAreNotAvailabeExceptions {
		return new ResponseEntity<>(ticketService.bookTicket(ticketDto), HttpStatus.CREATED);
	}

	@GetMapping("/ticket/movie/{movieId}")
	public ResponseEntity<?> getAllTickets(@PathVariable int movieId) {
		return new ResponseEntity<List<Ticket>>(ticketService.getAllTickets(movieId), HttpStatus.OK);
	}

}
