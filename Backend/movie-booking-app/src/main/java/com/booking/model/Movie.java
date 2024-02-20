package com.booking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

	@Id
	private int movieId;
	private String movieName;
	private String theatreName;
	private int totalSeats;

	/**
	 * @OneToMany(cascade = CascadeType.ALL)
	 * @JoinColumn(name = "movie_id", referencedColumnName = "movieId") private
	 *                  Set<Ticket> tickets;
	 */

	// @JsonIgnore
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ticket> tickets = new ArrayList<>();

}
