package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.*;

import javax.transaction.Transactional;

import com.bookstore.domain.PromotionalCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.ShoppingCartRepository;
import com.bookstore.core.repository.UserRepository;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;



@Component
@Transactional
public class ShoppingCartDAO extends AbstractDAO {

	@Autowired
	ShoppingCartRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		ShoppingCart user = (ShoppingCart)entity;
		repository.save(user);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		ShoppingCart user = (ShoppingCart)entity;
		repository.save(user);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		ShoppingCart user = (ShoppingCart)entity;
		repository.delete(user);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		 return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



}
