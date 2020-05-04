package com.adminportal.core.application.dto;

import java.math.BigDecimal;

public class ExchangeDTO {
	
	private Long orderId;
	private Long[] bookId;
	private Integer[] qty;
	private String justification;
	private BigDecimal valueExchange;
	
	
	public ExchangeDTO() {
		super();
	}
	
	
	public ExchangeDTO(Long orderId, Long[] bookId, Integer[] qty, String justification, BigDecimal valueExchange) {
		super();
		this.orderId = orderId;
		this.bookId = bookId;
		this.qty = qty;
		this.justification = justification;
		this.valueExchange = valueExchange;
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
