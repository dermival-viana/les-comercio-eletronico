package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.ExchangeCouponRepository;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.ExchangeCoupon;



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

}
