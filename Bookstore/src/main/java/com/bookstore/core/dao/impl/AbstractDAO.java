package com.bookstore.core.dao.impl;


import java.sql.SQLException;
import java.util.List;

import com.bookstore.core.IDAO;
import com.bookstore.domain.DomainEntity;

public abstract class AbstractDAO implements IDAO{ 

		
   	

	@SuppressWarnings("unchecked")
	protected <T> T noCast(Object entidade) {
		return (T) entidade;
	}

	
	
	
	
		
}
