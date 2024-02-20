package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.exception.ResourceNotFoundException;
import com.booking.exception.SeatsAreNotAvailabeExceptions;
import com.booking.model.Movie;
import com.booking.model.Ticket;
import com.booking.model.TicketDto;
import com.booking.repository.MovieRepository;
import com.booking.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private MovieRepository movieRepository;

	private Ticket mapToEntity(TicketDto ticketDto) {

		Ticket ticket = new Ticket();
		ticket.setMovieName(ticketDto.getMovieName());
		ticket.setTheatreName(ticketDto.getTheatreName());
		ticket.setBookedSeats(ticketDto.getNoOfSeats());

		return ticket;
	}

	private TicketDto mapToDTO(Ticket ticket) {
		TicketDto ticketDto = new TicketDto();

		if (ticket != null) {
			ticketDto.setTransactionId(ticket.getTransactionId());
			ticketDto.setMovieName(ticket.getMovieName());
			ticketDto.setTheatreName(ticket.getTheatreName());
			ticketDto.setNoOfSeats(ticket.getBookedSeats());
		}

		return ticketDto;
	}

	@Override
	public TicketDto bookTicket(TicketDto ticketDto) throws SeatsAreNotAvailabeExceptions {
		Movie movie = movieRepository.findByMovieNameAndTheatreName(ticketDto.getMovieName(),
				ticketDto.getTheatreName());
		Ticket ticket = mapToEntity(ticketDto);
		ticket.setTotalSeats(movie.getTotalSeats());

		List<Ticket> tickets = ticketRepository.findAllByMovieId(movie.getMovieId());

		if (tickets.isEmpty()) {
			ticket.setAvailableSeats(ticket.getTotalSeats() - ticket.getBookedSeats());
			ticket.setMovie(movie);
			Ticket saveTicket = ticketRepository.save(ticket);
			return mapToDTO(saveTicket);
		}

		int minAvailableSeats = tickets.get(0).getAvailableSeats();

		for (Ticket t : tickets) {
			minAvailableSeats = Math.min(minAvailableSeats, t.getAvailableSeats());
		}

		if (minAvailableSeats - ticket.getBookedSeats() >= 0) {
			ticket.setAvailableSeats(minAvailableSeats - ticket.getBookedSeats());
			ticket.setMovie(movie);
			Ticket saveTicket = ticketRepository.save(ticket);
			return mapToDTO(saveTicket);
		}

		throw new SeatsAreNotAvailabeExceptions();

	}

	@Override
	public List<Ticket> getAllTickets(int movieId) {

		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
		System.out.println("id: " + movie.getMovieId());
		List<Ticket> tickets = ticketRepository.findAllByMovieId(movieId);
		if (tickets.isEmpty())
			throw new ResourceNotFoundException("Ticket", "movie id", movieId);

		return tickets;

	}

}
