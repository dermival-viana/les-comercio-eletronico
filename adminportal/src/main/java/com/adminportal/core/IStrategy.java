package com.adminportal.core;

import com.adminportal.domain.DomainEntity;

import java.sql.SQLException;

public interface IStrategy 
{

	public boolean supports(Class<?> clazz);
	public String process(DomainEntity entity) throws SQLException, ClassNotFoundException;
	
}
