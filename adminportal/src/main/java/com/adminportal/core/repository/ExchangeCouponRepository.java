package com.adminportal.core.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminportal.domain.ExchangeCoupon;
@Repository
public interface ExchangeCouponRepository extends JpaRepository<ExchangeCoupon, Long>{

	Optional<ExchangeCoupon> findById(Long id);


}
