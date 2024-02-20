package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

	Movie findByMovieNameAndTheatreName(String movieName, String theatreName);
}
