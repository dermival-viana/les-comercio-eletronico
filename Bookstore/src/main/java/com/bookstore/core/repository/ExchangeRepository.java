package com.bookstore.core.repository;

import java.util.List;
import java.util.Optional;

import com.bookstore.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.Exchange;
@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long>{

	Optional<Exchange> findById(Long id);
    List<Exchange> findByOrder(Order order);

}
