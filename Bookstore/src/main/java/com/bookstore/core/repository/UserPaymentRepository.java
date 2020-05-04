package com.bookstore.core.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.UserPayment;

import java.util.Optional;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>{

    Optional<UserPayment> findById(Long id);
}
