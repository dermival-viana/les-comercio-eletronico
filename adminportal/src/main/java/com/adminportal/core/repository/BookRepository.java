package com.adminportal.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>{

    Optional<Book> findById(Long id);
}
