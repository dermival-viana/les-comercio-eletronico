package com.bookstore.core.dao.impl;


import com.bookstore.core.repository.PromotionalCouponRepository;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.ExchangeCoupon;
import com.bookstore.domain.PromotionalCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class PromotionalCouponDAO extends AbstractDAO {

	@Autowired
	PromotionalCouponRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		PromotionalCoupon pCoupon = (PromotionalCoupon)entity;
		repository.save(pCoupon);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		PromotionalCoupon pCoupon = (PromotionalCoupon)entity;
		repository.save(pCoupon);

	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		PromotionalCoupon pCoupon = (PromotionalCoupon)entity;

		repository.delete(pCoupon);
		
	}

	
	@Override

	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<PromotionalCoupon> optionalPromotionalCoupon = this.repository.findById(entity.getId());

		if(!optionalPromotionalCoupon.isPresent())
			throw new ClassNotFoundException("Não foi possivel localizar o Cupom Promocional com id " + entity.getId());

		return  optionalPromotionalCoupon.get();
	}

	public DomainEntity findByCode(DomainEntity entity) throws SQLException, ClassNotFoundException {
		PromotionalCoupon promotionalCoupon = (PromotionalCoupon)entity;
		String code = String.valueOf(promotionalCoupon.getCode());
		Optional<PromotionalCoupon> optionalPromotionalCoupon = this.repository.findByCode(code);

		if(!optionalPromotionalCoupon.isPresent())
			throw new ClassNotFoundException("Not possible find code Coupon ");

		return  optionalPromotionalCoupon.get();
	}

	public List<DomainEntity> findAllByActiveTrue(DomainEntity entity) {
		return  noCast(repository.findAllByActiveTrue());
	}
}
