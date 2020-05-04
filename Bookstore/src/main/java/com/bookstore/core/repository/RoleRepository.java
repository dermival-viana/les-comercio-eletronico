package com.bookstore.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.security.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}
