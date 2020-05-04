package com.adminportal.core.application.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.adminportal.domain.BillingAddress;
import com.adminportal.domain.CartItem;
import com.adminportal.domain.Payment;
import com.adminportal.domain.ShippingAddress;
import com.adminportal.domain.enun.StatusOrder;

public class OrderDTO {

	private Date orderDate;
	private Date shippingDate;
	private String shippingMethod;
	private StatusOrder status;
	private BigDecimal orderTotal;
	private List<CartItem> cartItemList;
	private ShippingAddress shippingAddress;
	private BillingAddress billingAddress;
	private Payment payment;
	
	
	public OrderDTO(Date orderDate, Date shippingDate, String shippingMethod, StatusOrder status, BigDecimal orderTotal,
                    List<CartItem> cartItemList, ShippingAddress shippingAddress, BillingAddress billingAddress,
                    Payment payment) {
		super();
		this.orderDate = orderDate;
		this.shippingDate = shippingDate;
		this.shippingMethod = shippingMethod;
		this.status = status;
		this.orderTotal = orderTotal;
		this.cartItemList = cartItemList;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.payment = payment;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public StatusOrder getStatus() {
		return status;
	}

	public void setStatus(StatusOrder statusOrder) {
		this.status = statusOrder;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public BillingAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
	
}
