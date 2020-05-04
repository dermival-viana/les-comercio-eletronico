package com.adminportal.core;






import java.sql.SQLException;
import java.util.List;

import com.adminportal.domain.Category;
import com.adminportal.domain.DomainEntity;




public interface IDAO {
	
	
	
	public void save(DomainEntity entity) throws SQLException;
	public void update(DomainEntity entity)throws SQLException;
	public void delete(DomainEntity entity)throws SQLException;
	public List<DomainEntity> findAll(DomainEntity entity)throws SQLException;
	public DomainEntity findById(DomainEntity entity) throws SQLException, ClassNotFoundException;
	

}
