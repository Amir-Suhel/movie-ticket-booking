package com.authentication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String secretQuestion;
	private String password;

}
