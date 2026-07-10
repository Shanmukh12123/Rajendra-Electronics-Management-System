package com.rajendraelectronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajendraelectronics.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
}
