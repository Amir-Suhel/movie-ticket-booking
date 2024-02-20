package com.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieDto {
	
	private int movieId;
	private String movieName;
	private String theatreName;
	private int totalSeats;
}
 