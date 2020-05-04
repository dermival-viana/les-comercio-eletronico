package com.bookstore.core;


import com.bookstore.core.aplication.Result;
import com.bookstore.domain.DomainEntity;

import java.sql.SQLException;

public interface IFacade {
	public Result save(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result update(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result delete(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result findAll(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public Result findById(DomainEntity entity) throws SQLException, ClassNotFoundException;
	
	//BookStore

	
}
