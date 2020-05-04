package com.adminportal.core.dao;

import com.adminportal.core.repository.BookRepository;
import com.adminportal.core.repository.PriceGroupRepository;
import com.adminportal.domain.Book;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.PriceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class PriceGroupDAO extends AbstractDAO {

	@Autowired
	PriceGroupRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		 PriceGroup priceGroup = (PriceGroup) entity;
		 priceGroup.setId(null);
		 repository.save(priceGroup);
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		PriceGroup priceGroup = (PriceGroup) entity;
		repository.save(priceGroup);
		
	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		PriceGroup priceGroup = (PriceGroup) entity;
		repository.delete(priceGroup);
		
	}

	
	@Override

	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		
		return noCast(repository.findAll());
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<PriceGroup> optionalPriceGroup = this.repository.findById(entity.getId());

		if(!optionalPriceGroup.isPresent())
			throw new ClassNotFoundException("Not possible find price group with id " + entity.getId());

		return  optionalPriceGroup.get();
	}



}
