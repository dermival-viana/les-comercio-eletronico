package com.adminportal.core.business.exchangeCoupon;

import java.sql.SQLException;
import java.util.UUID;

import com.adminportal.core.IDAO;

import com.adminportal.core.IStrategy;
import com.adminportal.core.dao.BookDAO;
import com.adminportal.core.dao.ExchangeCouponDAO;
import com.adminportal.domain.*;
import com.adminportal.domain.enun.StatusExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateGenerateExchangeCoupon implements IStrategy{

	@Autowired
	private ExchangeCouponDAO  exchangeCouponDAO;

	@Autowired
	private BookDAO bookDAO;

	@Autowired
	private IGenerateCouponHelper  generateCouponHelper;

	@Override
	public boolean supports(Class<?> clazz) {
		return Exchange.class.equals(clazz);
	}

	@Override
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Exchange exchange = (Exchange)entity;

		StringBuilder msg = new StringBuilder();

		if(exchange.getStatusExchange()== StatusExchange.TROCA_AUTORIZADA) {
			String codeCoupon = generateCouponHelper.generateCoupon();
			//after exchange returns items to stock
			for(CartItem item : exchange.getCartItemList()){
				Book book = item.getBook();
				Integer qtdInStock = book.getInStockNumber() + item.getQty();
				book.setInStockNumber(qtdInStock);
				bookDAO.update(book);
			}
			ExchangeCoupon exchangeCoupon = new ExchangeCoupon();
			exchangeCoupon.setUser(exchange.getUser());
			exchangeCoupon.setCode(codeCoupon);
			exchangeCoupon.setValue(exchange.getValueExchange());
			exchangeCoupon.setActive(true);
            exchangeCouponDAO.save(exchangeCoupon);

		}

		return msg.toString();
	}

}
