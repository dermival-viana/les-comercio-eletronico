package com.adminportal.core;

import com.adminportal.core.application.Result;
import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.Exchange;

import java.sql.SQLException;

public interface IFacade {
	public Result save(DomainEntity entity);
	public Result update(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result delete(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result findAll(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result findById(DomainEntity entity) throws SQLException, ClassNotFoundException;

	
	
}
