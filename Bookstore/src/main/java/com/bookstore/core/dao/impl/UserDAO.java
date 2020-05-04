package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.*;

import javax.transaction.Transactional;

import com.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.UserRepository;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.User;



@Component
@Transactional
public class UserDAO extends AbstractDAO {

	@Autowired
	UserRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 User user = (User)entity;
		repository.save(user);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		 User user = (User)entity;
		 repository.save(user);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		 User user = (User)entity;
		 repository.delete(user);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		 return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		User user = this.repository.findOne(entity.getId());

		return  user;
	}


}
