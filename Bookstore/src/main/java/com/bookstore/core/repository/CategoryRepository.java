package com.bookstore.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.Category;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findById(Long id);
}
