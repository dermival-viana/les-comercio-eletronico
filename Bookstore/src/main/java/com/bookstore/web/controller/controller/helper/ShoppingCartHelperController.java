
/**
 * @author marco
 *
 */
package com.bookstore.web.controller.controller.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bookstore.core.dao.impl.BookToCartItemDAO;
import com.bookstore.core.repository.BookToCartItemRepository;
import com.bookstore.core.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.IFacade;
import com.bookstore.core.aplication.Result;
import com.bookstore.domain.Book;
import com.bookstore.domain.BookToCartItem;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;

@Component
public class ShoppingCartHelperController {

	@Autowired
	private IFacade facade;

	@Autowired
	private Result result;
	@Autowired
	private BookToCartItemRepository bookToCartItemRepository;

	@Autowired
	private CartItemRepository cartItemRepository;



	@SuppressWarnings("unchecked")
	public CartItem addBookToCartItem(Book book, User user, int qty) throws SQLException, ClassNotFoundException {

		List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
		cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart()==user.getShoppingCart()).collect(Collectors.toList());

		for (CartItem cartItem : cartItemList) {
			if (book.getId() == cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
				facade.save(cartItem);
				return cartItem;
			}
		}

		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);

		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);

		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		facade.save(bookToCartItem);

		return cartItem;
	}

	public CartItem updateCartItem(CartItem cartItem) throws SQLException, ClassNotFoundException {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice())
				.multiply(new BigDecimal(cartItem.getQty()));

		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);

		facade.save(cartItem);

		return cartItem;
	}

	@SuppressWarnings("unchecked")
	public void removeCartItem(CartItem cartItem) throws SQLException, ClassNotFoundException {

		Optional<BookToCartItem> bookToCartItem = this.bookToCartItemRepository.findByCartItem(cartItem);
		if(!bookToCartItem.isPresent())
			throw new ClassNotFoundException("Not possible find book with id ");

		facade.delete(bookToCartItem.get());

		List<CartItem> cartItems = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
		cartItems = cartItems.stream().filter(cti -> cti.getId()==cartItem.getId()).collect(Collectors.toList());
		CartItem cartItempk = cartItems.get(0);

		facade.delete(cartItempk);

	}

	@SuppressWarnings("unchecked")
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) throws SQLException, ClassNotFoundException {
		BigDecimal cartTotal = new BigDecimal(0);

		List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
		cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart()==shoppingCart).collect(Collectors.toList());

		for (CartItem cartItem : cartItemList) {
			if (cartItem.getBook().getInStockNumber() > 0) {
				facade.save(cartItem);
				cartTotal = cartTotal.add(cartItem.getSubtotal());
			}
		}

		shoppingCart.setGrandTotal(cartTotal);

		facade.save(shoppingCart);

		return shoppingCart;
	}

	@SuppressWarnings("unchecked")
	public void clearShoppingCart(ShoppingCart shoppingCart) throws SQLException, ClassNotFoundException {
		List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
		cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart()==shoppingCart).collect(Collectors.toList());
		for (CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			facade.save(cartItem);
		}

		shoppingCart.setGrandTotal(new BigDecimal(0));

		facade.save(shoppingCart);
	}


}