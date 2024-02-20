package com.authentication.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.JwtResponse;
import com.authentication.dto.ResetPassword;
import com.authentication.dto.SignInRequest;
import com.authentication.dto.SignUpRequest;
import com.authentication.exception.EmailAlreadyTakenException;
import com.authentication.model.ERole;
import com.authentication.model.Role;
import com.authentication.model.User;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import com.authentication.service.UserDetailsImpl;
import com.authentication.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/signIn")
	public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		JwtResponse res = new JwtResponse();
		res.setJwtToken(jwt);
		res.setId(userDetails.getId());
		res.setFirstName(userDetails.getFirstName());
		res.setLastName(userDetails.getLastName());
		res.setEmail(userDetails.getEmail());
		res.setRoles(roles);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) throws EmailAlreadyTakenException {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new EmailAlreadyTakenException();
		}
		String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
		Set<Role> roles = new HashSet<>();
		Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
		if (userRole.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
		}
		roles.add(userRole.get());
		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setEmail(signUpRequest.getEmail());
		user.setSecretQuestion(signUpRequest.getSecretQuestion());
		user.setPassword(hashedPassword);
		user.setRoles(roles);
		userRepository.save(user);
		// return ResponseEntity.ok("User registered successfully!!");
		return ResponseEntity.ok(user);
	}

	@PutMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody ResetPassword resetPassword)
			throws CredentialNotFoundException {
		if (!userRepository.existsByEmailAndSecretQuestion(resetPassword.getEmail(),
				resetPassword.getSecretQuestion())) {
			// response.put("password changed", Boolean.FALSE);
			// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Secret Question or
			// email are wrong!!");
			// return ResponseEntity.badRequest(response);

			// return ResponseEntity.ok(response);
			throw new CredentialNotFoundException();
		}

		String hashedPassword = passwordEncoder.encode(resetPassword.getNewPassword());
		User user = userRepository.findByEmail(resetPassword.getEmail()).get();
		user.setPassword(hashedPassword);
		userRepository.save(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("password changed", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userRepository.findAll();
		if (!users.isEmpty()) {
			return ResponseEntity.ok(users);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user is found!!");
	}

}
