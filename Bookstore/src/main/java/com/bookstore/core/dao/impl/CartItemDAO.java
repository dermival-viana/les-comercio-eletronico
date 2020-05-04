package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.CartItemRepository;
import com.bookstore.core.repository.CategoryRepository;


@Component
@Transactional
public class CartItemDAO extends AbstractDAO {

	@Autowired
	CartItemRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		CartItem cartItem = (CartItem)entity;
		repository.save(cartItem);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		CartItem cartItem = (CartItem)entity;
		repository.save(cartItem);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		CartItem cartItem = (CartItem)entity;
		repository.delete(cartItem);
		
	}

	
	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		 
		return noCast(repository.findAll());
		
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<CartItem> optionalCartItem = this.repository.findById(entity.getId());

		if(!optionalCartItem.isPresent())
			throw new ClassNotFoundException("Not possible find cartItem with id " + entity.getId());

		return  optionalCartItem.get();
	}

	public List<DomainEntity> findByShoppingCart(DomainEntity entity) throws SQLException, ClassNotFoundException {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart = (ShoppingCart)entity;
		List<CartItem> cartItemList = this.repository.findByShoppingCart(shoppingCart);

		if(cartItemList.isEmpty())
			throw new ClassNotFoundException("Not possible find cartItem");

		return  noCast(cartItemList);
	}


}
