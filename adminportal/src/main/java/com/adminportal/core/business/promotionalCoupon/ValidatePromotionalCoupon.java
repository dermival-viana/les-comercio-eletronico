package com.adminportal.core.business.promotionalCoupon;

import com.adminportal.core.IStrategy;
import com.adminportal.core.business.exchangeCoupon.IGenerateCouponHelper;
import com.adminportal.core.dao.ExchangeCouponDAO;
import com.adminportal.core.dao.PromotionalCouponDAO;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.Exchange;
import com.adminportal.domain.ExchangeCoupon;
import com.adminportal.domain.PromotionalCoupon;
import com.adminportal.domain.enun.StatusExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class ValidatePromotionalCoupon implements IStrategy{

	@Autowired
	private PromotionalCouponDAO pCouponDAO;

	@Autowired
	private IGenerateCouponHelper  generateCouponHelper;

	@Override
	public boolean supports(Class<?> clazz) {
		return PromotionalCoupon.class.equals(clazz);
	}

	@Override
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException {
		PromotionalCoupon pCoupon = (PromotionalCoupon) entity;

		StringBuilder msg = new StringBuilder();

		if (pCoupon.getStartDate().equals(null)||pCoupon.getStartDate().equals("")) {
		    msg.append("start date is required");
		}
		if (pCoupon.getEndDate().equals(null)||pCoupon.getEndDate().equals("")) {
			msg.append("end date is required");
		}
		if (pCoupon.getValue().equals(null)||pCoupon.getValue().equals("")) {
			msg.append("coupon value is required");
		}
		if (pCoupon.getCode().equals(null)||pCoupon.getCode().equals("")) {
			msg.append("coupon code is required");
		}


		return msg.toString();
	}

}
