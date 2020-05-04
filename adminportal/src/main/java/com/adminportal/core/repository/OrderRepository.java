package com.adminportal.core.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.adminportal.domain.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query("select order from Order order where order.orderDate between ?1 and ?2 ")
      public List<Order> filterYear(Date start, Date end);

    Optional<Order> findById(Long id);
}
