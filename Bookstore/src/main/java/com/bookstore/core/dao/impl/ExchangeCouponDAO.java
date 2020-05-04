package com.bookstore.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bookstore.core.repository.ExchangeCouponRepository;
import com.bookstore.domain.DomainEntity;
import com.bookstore.domain.ExchangeCoupon;

@Component
@Transactional
public class ExchangeCouponDAO extends AbstractDAO {
	@Autowired
	private ExchangeCouponRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		ExchangeCoupon exchangeCoupon = (ExchangeCoupon)entity;
		repository.save(exchangeCoupon);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		ExchangeCoupon exchangeCoupon = (ExchangeCoupon)entity;
		repository.save(exchangeCoupon);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		ExchangeCoupon exchangeCoupon = (ExchangeCoupon)entity;
		repository.delete(exchangeCoupon);
		
	}

	
	@Override

	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException {
	
		return  noCast(repository.findById(entity.getId()));
	}



	public DomainEntity findByCode(DomainEntity entity) throws SQLException, ClassNotFoundException {
		ExchangeCoupon exchangeCoupon = (ExchangeCoupon)entity;
		String code = String.valueOf(exchangeCoupon.getCode());
		Optional<ExchangeCoupon> optionalExchangeCoupon = this.repository.findByCode(code);

		if(!optionalExchangeCoupon.isPresent())
			throw new ClassNotFoundException("Not possible find code Coupon ");

		return  optionalExchangeCoupon.get();
	}


	public  List<DomainEntity> findAllByActiveTrue(DomainEntity entity) {

		return  noCast(repository.findAllByActiveTrue());
	}

}
