package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.CategoryRepository;
import com.bookstore.domain.Category;
import com.bookstore.domain.DomainEntity;





@Component
@Transactional
public class CategoryDAO extends AbstractDAO {

	@Autowired
	CategoryRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 Category category = (Category)entity;
		repository.save(category);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		 Category category = (Category)entity;
		 repository.save(category);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		 Category category = (Category)entity;
		 repository.delete(category);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		return noCast(repository.findAll());
		
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<Category> optionalCategory = this.repository.findById(entity.getId());

		if(!optionalCategory.isPresent())
			throw new ClassNotFoundException("Not possible find category with id " + entity.getId());

		return  optionalCategory.get();
	}


}
