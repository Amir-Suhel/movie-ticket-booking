package com.booking.service;

import java.util.List;

import com.booking.exception.MovieIdAlreadyExistsExceptions;
import com.booking.model.Movie;

public interface MovieService {

	List<Movie> getAllMovies();

	Movie getMovieById(int movieId);

	void deleteMovieById(int movieId);

	Movie addMovie(Movie movie) throws MovieIdAlreadyExistsExceptions;

}
