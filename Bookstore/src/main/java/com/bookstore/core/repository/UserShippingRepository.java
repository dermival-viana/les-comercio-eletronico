package com.bookstore.core.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.UserShipping;

import java.util.Optional;

@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long> {


    Optional<UserShipping> findById(Long id);
}
