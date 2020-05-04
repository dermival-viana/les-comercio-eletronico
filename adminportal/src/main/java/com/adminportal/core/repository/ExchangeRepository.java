package com.adminportal.core.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminportal.domain.Exchange;
@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long>{

	
}
