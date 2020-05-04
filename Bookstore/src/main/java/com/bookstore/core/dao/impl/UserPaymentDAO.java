package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bookstore.core.repository.UserPaymentRepository;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.UserPayment;




@Component
@Transactional
public class UserPaymentDAO extends AbstractDAO {

	@Autowired
	UserPaymentRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		UserPayment userpayment = (UserPayment)entity;
		repository.save(userpayment);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		UserPayment userpayment = (UserPayment)entity;
		repository.save(userpayment);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		UserPayment userpayment = (UserPayment)entity;
		repository.delete(userpayment);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		 return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<UserPayment> optionalUserPayment = this.repository.findById(entity.getId());

		if(!optionalUserPayment.isPresent())
			throw new ClassNotFoundException("Not possible find UserPayment with id " + entity.getId());

		return  optionalUserPayment.get();
	}


}
