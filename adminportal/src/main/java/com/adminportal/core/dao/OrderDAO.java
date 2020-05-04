package com.adminportal.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.adminportal.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adminportal.core.repository.OrderRepository;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.Order;

@Component
@Transactional
public class OrderDAO extends AbstractDAO {

	@Autowired
	OrderRepository repository;

	@Override
	public void save(DomainEntity entity) throws SQLException {
		Order order = (Order) entity;
		order.setId(null);
		repository.save(order);

	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		Order order = (Order) entity;
		repository.save(order);

	}

	@Override
	public void delete(DomainEntity entity) throws SQLException {
		Order order = (Order) entity;
		repository.delete(order);

	}

	@Override
	public List<DomainEntity> findAll(DomainEntity entity) throws SQLException {
		if (entity.getId() == null) {
			return noCast(repository.findAll());
		}else {
			return noCast(repository.findOne(entity.getId()));
		}
          
	}

	@Override
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
		Optional<Order> optionalOrder = this.repository.findById(entity.getId());

		if(!optionalOrder.isPresent())
			throw new ClassNotFoundException("Not possible find order with id " + entity.getId());

		return  optionalOrder.get();
	}


}
