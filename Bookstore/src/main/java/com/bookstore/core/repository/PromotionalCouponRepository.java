package com.bookstore.core.repository;




import com.bookstore.domain.ExchangeCoupon;
import com.bookstore.domain.PromotionalCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionalCouponRepository extends JpaRepository<PromotionalCoupon, Long>{

    Optional<PromotionalCoupon> findById(Long id);

    List<PromotionalCoupon> findAllByActiveTrue();

    Optional<PromotionalCoupon> findByCode(String code);

}
