package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.adminportal.domain.Book;
import com.adminportal.domain.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.CategoryRepository;
import com.adminportal.domain.Category;
import com.adminportal.domain.DomainEntity;



@Component
@Transactional
public class CategoryDAO extends AbstractDAO {

	@Autowired
	CategoryRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 Category category = (Category)entity;
		 category.setId(null);
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
