package com.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TicketDto {

	private int transactionId;
	private String movieName;
	private String theatreName;
	private int noOfSeats;
}
