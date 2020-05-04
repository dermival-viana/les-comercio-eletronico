package com.bookstore.core.business.order;


import com.bookstore.core.IStrategy;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.Order;
import com.bookstore.web.controller.controller.CheckoutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class ValidatePaymentOrder implements IStrategy {
    @Autowired
	private CheckoutController checkoutController;
	@Override
	public boolean supports(Class<?> clazz) {
		return Order.class.equals(clazz);
	}

	@Override
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Order order = (Order)entity;
		StringBuilder msg = new StringBuilder();




		return msg.toString();
	//	return "";
	}

}
