package com.rajendraelectronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajendraelectronics.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
