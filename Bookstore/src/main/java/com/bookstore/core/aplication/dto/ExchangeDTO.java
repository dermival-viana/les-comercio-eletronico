package com.bookstore.core.aplication.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.bookstore.domain.User;

public class ExchangeDTO {
	
	@NotNull(message="O id do pedido é obrigatório")
	private Long orderId;
	private Long[] bookId;
	private Integer[] qty;
	private String justification;
	private BigDecimal valueExchange;
	@NotNull(message="O id do cliente é obrigatório")
	private User user;
	private String statusOrder;
	
	
	public ExchangeDTO() {
		super();
	}

	public ExchangeDTO(Long orderId) {
		this.orderId = orderId;
	}

	public ExchangeDTO(Long orderId, Long[] bookId, Integer[] qty, String justification, BigDecimal valueExchange,
					   User user, String statusOrder) {
		super();
		this.orderId = orderId;
		this.bookId = bookId;
		this.qty = qty;
		this.justification = justification;
		this.valueExchange = valueExchange;
		this.user = user;
		this.statusOrder = statusOrder;
	}





	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}





	public String getStatusOrder() {
		return statusOrder;
	}





	public void setStatusOrder(String statusOrder) {
		this.statusOrder = statusOrder;
	}





	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long[] getBookId() {
		return bookId;
	}
	public void setBookId(Long[] bookId) {
		this.bookId = bookId;
	}
	public Integer[] getQty() {
		return qty;
	}
	public void setQty(Integer[] qty) {
		this.qty = qty;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}


	public BigDecimal getValueExchange() {
		return valueExchange;
	}


	public void setValueExchange(BigDecimal valueExchange) {
		this.valueExchange = valueExchange;
	}
	
	

}
