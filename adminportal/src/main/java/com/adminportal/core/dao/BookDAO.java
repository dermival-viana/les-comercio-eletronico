package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.BookRepository;
import com.adminportal.domain.Book;
import com.adminportal.domain.Category;
import com.adminportal.domain.DomainEntity;



@Component
@Transactional
public class BookDAO extends AbstractDAO {

	@Autowired
	BookRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		Book book = (Book)entity;
		book.setId(null);
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
