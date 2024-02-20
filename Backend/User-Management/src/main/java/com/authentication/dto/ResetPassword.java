package com.authentication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPassword {

	private String email;
	private String secretQuestion;
	private String newPassword;
}
