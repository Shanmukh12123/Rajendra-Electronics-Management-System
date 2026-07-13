package com.rajendraelectronics.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajendraelectronics.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{

	List<Sale> findByRemainingAmountGreaterThan(Double amount);
	
	List<Sale> findBySaleDate(LocalDate saleDate);
	
	List<Sale> findBySaleDateBetween(
	        LocalDate startDate,
	        LocalDate endDate);
	
	List<Sale> findTop5ByOrderBySaleDateDesc();
}
