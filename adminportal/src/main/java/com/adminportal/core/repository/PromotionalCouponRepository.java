package com.adminportal.core.repository;



import com.adminportal.domain.PromotionalCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionalCouponRepository extends JpaRepository<PromotionalCoupon, Long>{

	Optional<PromotionalCoupon> findById(Long id);


}
