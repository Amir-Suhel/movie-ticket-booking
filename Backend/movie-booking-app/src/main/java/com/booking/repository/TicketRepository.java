package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.model.Ticket;

//@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Query(value = "select * from tickets t where t.movie_id= :movieId", nativeQuery = true)
	List<Ticket> findAllByMovieId(int movieId);

}
