package com.bookstore.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.BookToCartItem;
import com.bookstore.domain.CartItem;

import java.util.Optional;


@Repository
public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long> {
	Optional<BookToCartItem> findByCartItem(CartItem cartItem);
}
