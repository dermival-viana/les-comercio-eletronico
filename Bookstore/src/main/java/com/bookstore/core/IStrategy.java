package com.bookstore.core;

import com.bookstore.domain.DomainEntity;

import java.sql.SQLException;

public interface IStrategy 
{

	
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException;
	public boolean supports(Class<?> clazz);
}
