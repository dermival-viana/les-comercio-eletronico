package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.CartItemRepository;
import com.bookstore.core.repository.CategoryRepository;
import com.bookstore.core.repository.OrderRepository;


@Component
@Transactional
public class OrderDAO extends AbstractDAO {

	@Autowired
	OrderRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		Order order = (Order)entity;
		repository.save(order);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		Order order = (Order)entity;
		repository.save(order);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		Order order = (Order)entity;
		repository.delete(order);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		 
		return noCast(repository.findAll());
		
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<Order> optionalOrder = this.repository.findById(entity.getId());

		if(!optionalOrder.isPresent())
			throw new ClassNotFoundException("Not possible find order with id " + entity.getId());

		return  optionalOrder.get();
	}


}
