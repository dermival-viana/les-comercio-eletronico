package com.adminportal.core.dao;

import com.adminportal.core.repository.PromotionalCouponRepository;
import com.adminportal.domain.Category;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.PromotionalCoupon;
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
		pCoupon.setId(null);
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


}
