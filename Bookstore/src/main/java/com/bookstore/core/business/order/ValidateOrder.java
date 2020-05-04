package com.bookstore.core.business.order;


import com.bookstore.core.IStrategy;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class ValidateOrder implements IStrategy {

	@Override
	public boolean supports(Class<?> clazz) {
		return Order.class.equals(clazz);
	}

	@Override
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Order order = (Order)entity;
		StringBuilder msg = new StringBuilder();

        if(order.getPayment().equals(null) || order.getPayment().equals("")){
        	msg.append("Payment order is required");
		}


		return msg.toString();
	//	return "";
	}

}
