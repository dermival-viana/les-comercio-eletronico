package com.adminportal.core.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.domain.CartItem;
import com.adminportal.domain.Order;
import com.adminportal.domain.ShoppingCart;

@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	List<CartItem> findByOrder(Order order);

    Optional<CartItem> findById(Long id);
}
