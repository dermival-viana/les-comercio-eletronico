package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.UserRepository;
import com.adminportal.domain.Category;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.User;



@Component
@Transactional
public class UserDAO extends AbstractDAO {

	@Autowired
	UserRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 User user = (User)entity;
		 user.setId(null);
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
		Optional<User> optionalUser = this.repository.findById(entity.getId());

		if(!optionalUser.isPresent())
			throw new ClassNotFoundException("Not possible find user with id " + entity.getId());

		return  optionalUser.get();
	}


}
