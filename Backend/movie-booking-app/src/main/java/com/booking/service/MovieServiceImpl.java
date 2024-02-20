package com.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.exception.MovieIdAlreadyExistsExceptions;
import com.booking.exception.ResourceNotFoundException;
import com.booking.model.Movie;
import com.booking.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie getMovieById(int movieId) {
		return movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
	}

	@Override
	public Movie addMovie(Movie movie) throws MovieIdAlreadyExistsExceptions {
		Optional<Movie> existMovie = movieRepository.findById(movie.getMovieId());
		if (existMovie.isPresent())
			throw new MovieIdAlreadyExistsExceptions();
		return movieRepository.save(movie);
	}

	@Override
	public void deleteMovieById(int movieId) {
		movieRepository.deleteById(movieId);
	}

}
