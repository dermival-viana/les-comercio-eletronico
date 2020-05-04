package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.PromotionalCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.BookRepository;
import com.bookstore.domain.Book;
import com.bookstore.domain.Category;
import com.bookstore.domain.DomainEntity;



@Component
@Transactional
public class BookDAO extends AbstractDAO {

	@Autowired
	BookRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 Book book = (Book)entity;
		repository.save(book);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		 Book book = (Book)entity;
		 repository.save(book);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		 Book book = (Book)entity;
		 repository.delete(book);
		
	}

	
	@Override

	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		Book book = (Book)entity;
		if(!book.getCategory().equals(null) && !book.getCategory().isEmpty()){
			List<Category> categoryList = new ArrayList<>();
			for(Category category : book.getCategory()){
				categoryList.add(category);
			}
			return noCast(repository.findByBookCategory(categoryList));
		}

		return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
        Optional<Book> optionalBook = this.repository.findById(entity.getId());

        if(!optionalBook.isPresent())
            throw new ClassNotFoundException("Not possible find book with id " + entity.getId());

        return  optionalBook.get();
	}



}
