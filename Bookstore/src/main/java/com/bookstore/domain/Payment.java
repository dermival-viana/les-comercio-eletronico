package com.bookstore.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Payment extends DomainEntity {

	private static final long serialVersionUID = 1L;
	

	private String type;
	private String cardName;
	private String cardNumber;
	private int expiryMonth;
	private int expiryYear;
	private int cvc;
	private String holderName;

	@OneToOne
	private PaymentCoupon paymentCoupon;

	@OneToOne
	private PaymentTicket paymentTicket;

	
	@OneToOne
	private Order order;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment")
	private UserBilling userBilling;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public int getCvc() {
		return cvc;
	}

	public void setCvc(int cvc) {
		this.cvc = cvc;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public UserBilling getUserBilling() {
		return userBilling;
	}

	public void setUserBilling(UserBilling userBilling) {
		this.userBilling = userBilling;
	}

	public PaymentCoupon getPaymentCoupon() {
		return paymentCoupon;
	}

	public void setPaymentCoupon(PaymentCoupon paymentCoupon) {
		this.paymentCoupon = paymentCoupon;
	}

	public PaymentTicket getPaymentTicket() {
		return paymentTicket;
	}

	public void setPaymentTicket(PaymentTicket paymentTicket) {
		this.paymentTicket = paymentTicket;
	}
}