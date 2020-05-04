package com.adminportal.core.business.exchangeCoupon;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateCouponHelper implements IGenerateCouponHelper {

	@Override
	public String generateCoupon() {
		
		String CODECOUPON = "ABCEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder sb = new StringBuilder();
		Random rnd = new Random();
		
		while (sb.length()<18) {
			int index= (int) (rnd.nextFloat()*CODECOUPON.length());
			sb.append(CODECOUPON.charAt(index));
		}
		String code = sb.toString();
		
		return code;
	}

}
