package com.adminportal.core.dao;


import com.adminportal.core.IDAO;

public abstract class AbstractDAO implements IDAO{ 

		
	
	

	@SuppressWarnings("unchecked")
	protected <T> T noCast(Object entidade) {
		return (T) entidade;
	}

	
	
	
	
		
}
