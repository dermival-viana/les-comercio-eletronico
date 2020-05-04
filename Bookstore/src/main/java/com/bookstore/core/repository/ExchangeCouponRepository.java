package com.bookstore.core.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookstore.domain.ExchangeCoupon;
@Repository
public interface ExchangeCouponRepository extends JpaRepository<ExchangeCoupon, Long>{

	List<ExchangeCoupon> findById(Long id);

	List<ExchangeCoupon> findAllByActiveTrue();

	Optional<ExchangeCoupon> findByCode(String code);
}
