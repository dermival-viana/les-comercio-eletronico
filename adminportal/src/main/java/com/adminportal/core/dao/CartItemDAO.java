package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.adminportal.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.CartItemRepository;
import com.adminportal.domain.CartItem;
import com.adminportal.domain.DomainEntity;





@Component
@Transactional
public class CartItemDAO extends AbstractDAO {

	@Autowired
	CartItemRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		CartItem cartItem = (CartItem)entity;
		cartItem.setId(null);
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

}
