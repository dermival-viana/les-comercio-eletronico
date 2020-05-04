package com.bookstore.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	Optional<User> findByEmail(String email);


    Optional<User> findById(Long id);
}
