package com.bookstore.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartItem extends DomainEntity{

	private static final long serialVersionUID = 1L;
	

	private int qty;
	private BigDecimal subtotal;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@OneToMany(mappedBy = "cartItem")
	@JsonIgnore
	private List<BookToCartItem> bookToCartItemList;
	
	@ManyToOne
	@JoinColumn(name="shopping_cart_id")
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	
	public CartItem() {
		super();
		
	}

	public CartItem(Long id) {
		super(id);
	
	}


	public CartItem(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public CartItem(Order order) {
		this.order = order;
	}

	public CartItem(int qty, BigDecimal subtotal, Book book, List<BookToCartItem> bookToCartItemList,
			ShoppingCart shoppingCart, Order order) {
		super();
		this.qty = qty;
		this.subtotal = subtotal;
		this.book = book;
		this.bookToCartItemList = bookToCartItemList;
		this.shoppingCart = shoppingCart;
		this.order = order;
	}



    public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<BookToCartItem> getBookToCartItemList() {
		return bookToCartItemList;
	}

	public void setBookToCartItemList(List<BookToCartItem> bookToCartItemList) {
		this.bookToCartItemList = bookToCartItemList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
