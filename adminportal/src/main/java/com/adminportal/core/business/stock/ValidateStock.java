package com.adminportal.core.business.stock;



import com.adminportal.core.IStrategy;
import com.adminportal.core.dao.BookDAO;
import com.adminportal.domain.Book;
import com.adminportal.domain.CartItem;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.Exchange;
import com.adminportal.domain.enun.StatusExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class ValidateStock implements IStrategy {

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
