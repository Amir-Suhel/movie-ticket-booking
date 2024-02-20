package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.MovieIdAlreadyExistsExceptions;
import com.booking.model.Movie;
import com.booking.service.MovieService;

@RestController
@RequestMapping("/api/v1/moviebooking")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping("/all")
	public List<Movie> getAllMovies() {
		return movieService.getAllMovies();
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<Movie> getMovieById(@PathVariable int movieId) {

		return new ResponseEntity<>(movieService.getMovieById(movieId), HttpStatus.OK);
	}

	@PostMapping("/addmovie")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws MovieIdAlreadyExistsExceptions {

		return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{movieId}")
	public void deleteMovieById(@PathVariable int movieId) {
		movieService.deleteMovieById(movieId);
	}
}
