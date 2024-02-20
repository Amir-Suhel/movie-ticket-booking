package com.authentication;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.model.ERole;
import com.authentication.model.Role;
import com.authentication.model.User;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;

@Service
public class StartUp implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		Optional<Role> optRoleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
		if (optRoleAdmin.isEmpty()) {
			Role role = new Role();
			role.setName(ERole.ROLE_ADMIN);
			roleRepository.save(role);
		}

		Optional<Role> optRoleUser = roleRepository.findByName(ERole.ROLE_USER);
		if (optRoleUser.isEmpty()) {
			Role role = new Role();
			role.setName(ERole.ROLE_USER);
			roleRepository.save(role);
		}

		// create one admin user
		if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
			User user = new User();
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setEmail("admin@gmail.com");
			user.setSecretQuestion("Dog");
			user.setPassword(passwordEncoder.encode("admin"));
			if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
				Role role = new Role();
				role.setName(ERole.ROLE_ADMIN);
				roleRepository.save(role);
			}
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
			user.setRoles(roles);

			userRepository.save(user);

		}
	}
}