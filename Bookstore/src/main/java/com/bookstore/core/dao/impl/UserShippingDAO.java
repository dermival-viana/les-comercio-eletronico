package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.*;

import javax.transaction.Transactional;

import com.bookstore.domain.UserPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.UserRepository;
import com.bookstore.core.repository.UserShippingRepository;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.User;
import com.bookstore.domain.UserShipping;



@Component
@Transactional
public class UserShippingDAO extends AbstractDAO {

	@Autowired
	UserShippingRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		UserShipping userShipping = (UserShipping)entity;
		repository.save(userShipping);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		UserShipping userShipping = (UserShipping)entity;
		repository.save(userShipping);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		UserShipping userShipping = (UserShipping)entity;
		repository.delete(userShipping);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		 return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<UserShipping> optionalUserShipping = this.repository.findById(entity.getId());

		if(!optionalUserShipping.isPresent())
			throw new ClassNotFoundException("Not possible find UserShipping with id " + entity.getId());

		return  optionalUserShipping.get();
	}

	
}
