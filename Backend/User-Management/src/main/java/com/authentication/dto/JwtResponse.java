package com.authentication.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {

	private String jwtToken;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
}
