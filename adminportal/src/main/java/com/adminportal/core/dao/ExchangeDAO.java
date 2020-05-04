package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import com.adminportal.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.ExchangeRepository;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.Exchange;



@Component
@Transactional
public class ExchangeDAO extends AbstractDAO {

	@Autowired
	ExchangeRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 Exchange exchange = (Exchange)entity;
		 exchange.setId(null);
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
	public DomainEntity findById(DomainEntity entity) throws SQLException {
	
		return  noCast(repository.findOne(entity.getId()));
	}

}
