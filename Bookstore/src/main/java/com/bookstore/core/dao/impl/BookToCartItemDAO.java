package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bookstore.domain.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.repository.BookRepository;
import com.bookstore.core.repository.BookToCartItemRepository;
import com.bookstore.domain.Book;
import com.bookstore.domain.BookToCartItem;
import com.bookstore.domain.DomainEntity;



@Component
@Transactional
public class BookToCartItemDAO extends AbstractDAO {

	@Autowired
	BookToCartItemRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 BookToCartItem bookTocartItem = (BookToCartItem)entity;
		 repository.save(bookTocartItem);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		 BookToCartItem bookTocartItem = (BookToCartItem)entity;
		 repository.save(bookTocartItem);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		 BookToCartItem bookTocartItem = (BookToCartItem)entity;
		 repository.delete(bookTocartItem);
		
	}

	
	@Override

	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		CartItem cartItem = new CartItem();
		cartItem = (CartItem)entity;
		Optional<BookToCartItem> optionalBookToCartItem = this.repository.findByCartItem(cartItem);

		if(!optionalBookToCartItem.isPresent())
			throw new ClassNotFoundException("Not possible find bookToCartItem ");

		return  optionalBookToCartItem.get();
	}

	public DomainEntity findByCartItem(DomainEntity entity) throws SQLException, ClassNotFoundException {
		CartItem cartItem = new CartItem();
		cartItem = (CartItem)entity;
		Optional<BookToCartItem> optionalBookToCartItem = this.repository.findByCartItem(cartItem);

		if(!optionalBookToCartItem.isPresent())
			throw new ClassNotFoundException("Not possible find bookToCartItem ");

		return  optionalBookToCartItem.get();
	}


}
