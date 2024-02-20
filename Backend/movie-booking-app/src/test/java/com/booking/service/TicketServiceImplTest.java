package com.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.booking.exception.ResourceNotFoundException;
import com.booking.exception.SeatsAreNotAvailabeExceptions;
import com.booking.model.Movie;
import com.booking.model.Ticket;
import com.booking.model.TicketDto;
import com.booking.repository.MovieRepository;
import com.booking.repository.TicketRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class TicketServiceImplTest {

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private MovieRepository movieRepository;

	@InjectMocks
	private TicketServiceImpl ticketService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ticketService).build();
	}

	@Test
	public void bookTicketSuccess() throws SeatsAreNotAvailabeExceptions {

		TicketDto ticketDto = new TicketDto();
		ticketDto.setTransactionId(1);
		ticketDto.setMovieName("RRR");
		ticketDto.setTheatreName("LX Cinema");
		ticketDto.setNoOfSeats(2);

		Movie movie = new Movie();
		movie.setMovieId(1);
		movie.setMovieName("RRR");
		movie.setTheatreName("LX Cinema");
		movie.setTotalSeats(10);

		Ticket ticket = new Ticket();
		ticket.setTransactionId(1);
		ticket.setMovieName("RRR");
		ticket.setTheatreName("LX Cinema");
		ticket.setTotalSeats(10);
		ticket.setAvailableSeats(8);
		ticket.setBookedSeats(2);
		ticket.setMovie(movie);

		when(movieRepository.findByMovieNameAndTheatreName("RRR", "LX Cinema")).thenReturn(movie);

		when(ticketRepository.findAllByMovieId(1)).thenReturn(Collections.emptyList());
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

		TicketDto result = ticketService.bookTicket(ticketDto);

		assertNotNull(result);
		assertEquals(ticketDto.getMovieName(), result.getMovieName());
		assertEquals(ticketDto.getTheatreName(), result.getTheatreName());
		assertEquals(ticketDto.getNoOfSeats(), result.getNoOfSeats());
		assertNotNull(result.getTransactionId());
		verify(movieRepository, times(1)).findByMovieNameAndTheatreName("RRR", "LX Cinema");
		verify(ticketRepository, times(1)).findAllByMovieId(1);
		verify(ticketRepository, times(1)).save(any(Ticket.class));
	}

	@Test
	void bookTicketThrowsSeatsAreNotAvailableException() throws SeatsAreNotAvailabeExceptions {

		String movieName = "Movie 1";
		String theatreName = "Theatre 1";
		int totalSeats = 100;
		int bookedSeats = 101;

		TicketDto ticketDto = new TicketDto();
		ticketDto.setMovieName(movieName);
		ticketDto.setTheatreName(theatreName);
		ticketDto.setNoOfSeats(bookedSeats);

		Movie movie = new Movie();
		movie.setMovieId(1);
		movie.setMovieName(movieName);
		movie.setTheatreName(theatreName);
		movie.setTotalSeats(totalSeats);

		List<Ticket> existingTickets = new ArrayList<>();
		Ticket existingTicket = new Ticket();
		existingTicket.setAvailableSeats(totalSeats - 2);
		existingTickets.add(existingTicket);

		when(movieRepository.findByMovieNameAndTheatreName(eq(movieName), eq(theatreName))).thenReturn(movie);
		when(ticketRepository.findAllByMovieId(eq(movie.getMovieId()))).thenReturn(existingTickets);

		assertThrows(SeatsAreNotAvailabeExceptions.class, () -> ticketService.bookTicket(ticketDto));
		verify(movieRepository, times(1)).findByMovieNameAndTheatreName(eq(movieName), eq(theatreName));
		verify(ticketRepository, times(1)).findAllByMovieId(eq(movie.getMovieId()));
		verify(ticketRepository, never()).save(any(Ticket.class));
	}

	@Test
	void getAllTicketsByMovieId() {

		int movieId = 1;
		Movie movie = new Movie();
		movie.setMovieId(movieId);
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(new Ticket());

		when(movieRepository.findById(eq(movieId))).thenReturn(Optional.of(movie));
		when(ticketRepository.findAllByMovieId(eq(movieId))).thenReturn(tickets);

		List<Ticket> result = ticketService.getAllTickets(movieId);

		assertEquals(tickets.size(), result.size());
		verify(movieRepository, times(1)).findById(eq(movieId));
		verify(ticketRepository, times(1)).findAllByMovieId(eq(movieId));
	}

	@Test
	void getAllTicketsThrowsResourceNotFoundException() {
		int movieId = 1;
		when(movieRepository.findById(eq(movieId))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> ticketService.getAllTickets(movieId));
		verify(movieRepository, times(1)).findById(eq(movieId));
		verify(ticketRepository, never()).findAllByMovieId(anyInt());
	}
}
