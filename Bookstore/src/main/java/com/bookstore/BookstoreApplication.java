package com.bookstore;



import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.core.service.UserService;
import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.web.controller.utility.SecurityUtility;



@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user = new User();
		user.setFirstName("John");
		user.setLastName("Adams");
		user.setUsername("jAdams");
		user.setCpf("778.089.380-41");
		user.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user.setEmail("jadams@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role= new Role();
		role.setRoleId(0);
		role.setName("ROLE_USER");
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
	
	}
	
	
}
