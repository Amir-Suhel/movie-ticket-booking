package com.booking.service;

import java.util.List;

import com.booking.exception.SeatsAreNotAvailabeExceptions;
import com.booking.model.Ticket;
import com.booking.model.TicketDto;

public interface TicketService {

	TicketDto bookTicket(TicketDto ticketDto) throws SeatsAreNotAvailabeExceptions;

	List<Ticket> getAllTickets(int movieId);

}
