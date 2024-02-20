package com.booking.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.booking.exception.SeatsAreNotAvailabeExceptions;
import com.booking.model.Movie;
import com.booking.model.Ticket;
import com.booking.model.TicketDto;
import com.booking.service.MovieService;
import com.booking.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class TicketControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private TicketController ticketController;

	@Mock
	private MovieService movieService;

	@Mock
	private TicketService ticketService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
	}

	public Movie buildMovie() {
		return Movie.builder().movieId(1).movieName("RRR").theatreName("Aman PVR").totalSeats(120).build();
	}

	@Test
	public void bookTicket() throws JsonProcessingException, Exception {
		Movie movie = buildMovie();
		TicketDto ticketDto = new TicketDto();
		ticketDto.setTransactionId(1);
		ticketDto.setMovieName(movie.getMovieName());
		ticketDto.setTheatreName(movie.getTheatreName());
		ticketDto.setNoOfSeats(10);
		when(ticketService.bookTicket(ticketDto)).thenReturn(ticketDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/moviebooking/ticket/book")
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(ticketDto)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

	}

	@Test
	public void bookTicketFailure() throws Exception {
		TicketDto ticketDto = new TicketDto();
		when(ticketService.bookTicket(ticketDto)).thenThrow(SeatsAreNotAvailabeExceptions.class);
		assertThrows(SeatsAreNotAvailabeExceptions.class, () -> ticketController.bookTicket(ticketDto));
		verify(ticketService, times(1)).bookTicket(ticketDto);
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/moviebooking/ticket/movie/{movieId}", ticketDto)
//				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void getAllTicketsByMovieId() throws Exception {
		Movie movie = new Movie();
		movie.setMovieId(1);
		movie.setMovieName("RRR");
		movie.setTheatreName("LX Cinema");
		movie.setTotalSeats(120);

		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setTransactionId(1);
		ticket1.setMovieName(movie.getMovieName());
		ticket1.setTheatreName(movie.getTheatreName());
		ticket1.setTotalSeats(movie.getTotalSeats());
		ticket1.setBookedSeats(10);
		ticket1.setAvailableSeats(110);

		Ticket ticket2 = new Ticket();
		ticket2.setTransactionId(1);
		ticket2.setMovieName(movie.getMovieName());
		ticket2.setTheatreName(movie.getTheatreName());
		ticket2.setTotalSeats(movie.getTotalSeats());
		ticket2.setBookedSeats(5);
		ticket2.setAvailableSeats(105);

		tickets.add(ticket1);
		tickets.add(ticket2);
		movie.setTickets(tickets);

		when(ticketService.getAllTickets(movie.getMovieId())).thenReturn((List<Ticket>) tickets);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/moviebooking/ticket/movie/{movieId}", movie.getMovieId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

	}
}
