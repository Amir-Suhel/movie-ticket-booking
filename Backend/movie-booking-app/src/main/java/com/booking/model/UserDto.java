package com.booking.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	
	private String email;
	private String password; 
}
