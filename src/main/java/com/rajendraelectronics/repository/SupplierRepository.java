package com.rajendraelectronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajendraelectronics.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	
}
