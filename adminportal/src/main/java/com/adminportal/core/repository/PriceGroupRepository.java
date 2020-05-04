package com.adminportal.core.repository;


import com.adminportal.domain.PriceGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceGroupRepository extends JpaRepository<PriceGroup, Long>{

    Optional<PriceGroup> findById(Long id);
}
