package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.ExchangeRepository;


@Component
@Transactional
public class ExchangeDAO extends AbstractDAO {

	@Autowired
	ExchangeRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 Exchange exchange = (Exchange)entity;
		 repository.save(exchange);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		Exchange exchange = (Exchange)entity;
		 repository.save(exchange);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		Exchange exchange = (Exchange)entity;
		 repository.delete(exchange);
		
	}

	
	@Override

	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {

		Optional<Exchange> optionalExchange = this.repository.findById(entity.getId());

		if(!optionalExchange.isPresent())
			throw new ClassNotFoundException("Not possible find book with id " + entity.getId());

		return  optionalExchange.get();
	}
/*
	public List<Exchange> findByOrder(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Order order = new Order();
		order = (Order)entity;
		List<Exchange> exchangeList = this.repository.findByOrder(order);

		if(exchangeList.isEmpty())
			throw new ClassNotFoundException("Not possible find Exchange");

		return  noCast(exchangeList);
	}*/



}
