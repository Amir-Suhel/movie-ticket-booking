package com.booking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.booking.exception.MovieIdAlreadyExistsExceptions;
import com.booking.exception.ResourceNotFoundException;
import com.booking.model.Movie;
import com.booking.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private MovieController movieController;

	@Mock
	private MovieService movieService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
	}

	List<Movie> movieList = new ArrayList<>();

	@Test
	public void getAllMovies() throws Exception {
		Movie movie = Movie.builder().movieId(1).movieName("Iron Man").theatreName("Aman PVR").totalSeats(120).build();
		movieList.add(movie);

		when(movieService.getAllMovies()).thenReturn(movieList);

		List<Movie> movies = movieService.getAllMovies();
		assertEquals(movieList, movies);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/moviebooking/all").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

//	@Test
//	public void getAllMoviesNotFound() throws Exception {
//		movieList.clear();
//		when(movieService.getAllMovies()).thenReturn(movieList);
//		assertEquals(0, movieList.size());
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/moviebooking/all").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isNotFound());
//	}

	@Test
	public void getMovieById() throws Exception {
		Movie movie = Movie.builder().movieId(1).movieName("Iron Man").theatreName("Aman PVR").totalSeats(120).build();
		movieList.add(movie);
		when(movieService.getMovieById(movie.getMovieId())).thenReturn(movie);
		Movie movieResponse = movieService.getMovieById(movie.getMovieId());
		assertEquals(movie, movieResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/moviebooking/movie/{movieId}", movie.getMovieId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void getMovieByIdNotFound() throws Exception {
		int movieId = 100;
		when(movieService.getMovieById(movieId)).thenThrow(ResourceNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/moviebooking/movie/{movieId}", movieId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		
	}

	@Test
	public void addMovie() throws JsonProcessingException, Exception {
		movieList.clear();
		Movie movie = Movie.builder().movieId(1).movieName("Iron Man").theatreName("Aman PVR").totalSeats(120).build();
		movieList.add(movie);

		when(movieService.addMovie(any())).thenReturn(movie);
		assertEquals(1, movieList.size());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/moviebooking/addmovie")
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(movie)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	public void addMovieWithException() throws JsonProcessingException, Exception {
		Movie movie = Movie.builder().movieId(1).movieName("RRR").theatreName("Aman PVR").totalSeats(120).build();
		when(movieService.addMovie(any())).thenThrow(MovieIdAlreadyExistsExceptions.class);
		// MvcResult result =
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/moviebooking/addmovie")
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(movie)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		// assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void deleteMovieById() throws Exception {
		movieList.clear();
		Movie movie1 = Movie.builder().movieId(1).movieName("RRR").theatreName("Aman PVR").totalSeats(120).build();
		Movie movie2 = Movie.builder().movieId(2).movieName("Harry Potter").theatreName("Vijay Talkiz").totalSeats(120).build();
		movieList.add(movie1);
		movieList.add(movie2);
		int movieId = 1;
		doNothing().when(movieService).deleteMovieById(movieId);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/moviebooking//delete/{movieId}", movieId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());;
		
	}

}
