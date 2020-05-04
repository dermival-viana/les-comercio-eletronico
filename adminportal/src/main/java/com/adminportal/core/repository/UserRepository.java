package com.adminportal.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);

	User findByEmail(String email);

    Optional<User> findById(Long id);
}
