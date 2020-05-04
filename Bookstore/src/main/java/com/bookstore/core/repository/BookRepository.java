package com.bookstore.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.Book;
import com.bookstore.domain.Category;
import com.bookstore.domain.DomainEntity;
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{


	Optional<Book> findById(Long id);

	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Book obj, IN (obj.category) category where category in (:searchList)")
	public List<Book> findByBookCategory(@Param("searchList") List<Category> category);

	List<Book> findByTitleContaining(String title);


}
