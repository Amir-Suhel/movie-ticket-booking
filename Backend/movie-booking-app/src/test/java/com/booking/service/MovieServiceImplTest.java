package com.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.booking.exception.MovieIdAlreadyExistsExceptions;
import com.booking.exception.ResourceNotFoundException;
import com.booking.model.Movie;
import com.booking.repository.MovieRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieServiceImplTest {

	@Mock
	private MovieRepository movieRepository;

	@InjectMocks
	private MovieServiceImpl movieService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(movieService).build();
	}

	List<Movie> movieList = new ArrayList<>();

	@Test
	public void getAllMoviesTest() {
		Movie movie = Movie.builder().movieId(1).movieName("RRR").theatreName("LX Cinema").totalSeats(120).build();
		movieList.add(movie);

		when(movieRepository.findAll()).thenReturn(movieList);
		List<Movie> movies = movieService.getAllMovies();
		assertEquals(movieList, movies);
	}

	@Test
	public void getAllMoviesFailure() {
		when(movieRepository.findAll()).thenReturn(null);
		List<Movie> movies = movieService.getAllMovies();
		assertNull(movies);
	}

	@Test
	public void getMovieByIdSuccess() {
		Movie movie = Movie.builder().movieId(1).movieName("RRR").theatreName("LX Cinema").totalSeats(120).build();
		when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
		Movie response = movieService.getMovieById(movie.getMovieId());
		assertEquals(movie, response);
	}

	@Test
	public void getMovieByIdFailure() {
		int movieId = 100;
		String errorMsg = "Movie not found with id : '100'";
		when(movieRepository.findById(movieId)).thenThrow(new ResourceNotFoundException("Movie", "id", movieId));
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> movieService.getMovieById(movieId));
		assertEquals(errorMsg, exception.getMessage());

	}

	@Test
	public void deleteMovie() {
		// Movie movie = Movie.builder().movieId(1).movieName("RRR").theatreName("LX
		// Cinema").totalSeats(120).build();
		int movieId = 1;
		doNothing().when(movieRepository).deleteById(movieId);
		movieService.deleteMovieById(movieId);
		verify(movieRepository, times(1)).deleteById(movieId);
	}

	@Test
	public void addMovieSuccess() throws MovieIdAlreadyExistsExceptions {
		movieList.clear();
		Movie movie = Movie.builder().movieId(1).movieName("RRR").theatreName("LX Cinema").totalSeats(120).build();
		movieList.add(movie);
		when(movieRepository.save(any())).thenReturn(movie);
		Movie response = movieService.addMovie(movie);
		assertEquals(movie, response);
	}


	@Test
	public void addMovieFailure() throws MovieIdAlreadyExistsExceptions {
		Movie movie = Movie.builder().movieId(5).movieName("Harry Potter").theatreName("ABC Cinema").totalSeats(120)
				.build();
		int movieId = 5;
		when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

		assertThrows(MovieIdAlreadyExistsExceptions.class, () -> movieService.addMovie(movie));
		verify(movieRepository, times(1)).findById(movieId);
		verify(movieRepository, never()).save(movie);

	}

}
