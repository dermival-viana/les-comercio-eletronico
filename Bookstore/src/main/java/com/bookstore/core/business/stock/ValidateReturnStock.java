package com.bookstore.core.business.stock;


import com.bookstore.core.IStrategy;
import com.bookstore.core.dao.impl.BookDAO;
import com.bookstore.domain.Book;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.Exchange;
import com.bookstore.domain.enuns.StatusExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class ValidateReturnStock implements IStrategy {

	@Autowired
	private BookDAO bookDAO;


	@Override
	public boolean supports(Class<?> clazz) {
		return Exchange.class.equals(clazz);
	}

	@Override
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Exchange exchange = (Exchange)entity;

		StringBuilder msg = new StringBuilder();

		if(exchange.getStatusExchange()== StatusExchange.TROCA_AUTORIZADA) {

				//after exchange returns items to stock
				for(CartItem item : exchange.getCartItemList()){
					Book book = item.getBook();
					Integer qtdInStock = book.getInStockNumber() + item.getQty();
					book.setInStockNumber(qtdInStock);
					bookDAO.update(book);
				}

		}

		return msg.toString();
	}

}
