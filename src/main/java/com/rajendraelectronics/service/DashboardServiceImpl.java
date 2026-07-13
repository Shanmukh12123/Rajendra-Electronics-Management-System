package com.rajendraelectronics.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.dto.DashboardResponseDto;
import com.rajendraelectronics.entity.Purchase;
import com.rajendraelectronics.entity.Sale;
import com.rajendraelectronics.repository.PurchaseRepository;
import com.rajendraelectronics.repository.SaleRepository;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	private final SaleRepository saleRepository;
	private final PurchaseRepository purchaseRepository;
	public DashboardServiceImpl(SaleRepository saleRepository, PurchaseRepository purchaseRepository) {
		super();
		this.saleRepository = saleRepository;
		this.purchaseRepository = purchaseRepository;
	}
	
	@Override
	public DashboardResponseDto getDashboardData() {
		
		// Today's Sales
		LocalDate today = LocalDate.now();

		Double todaySales =
		        saleRepository.findBySaleDate(today)
		                .stream()
		                .mapToDouble(Sale::getTotalAmount)
		                .sum();
		
		// Monthly Sales
		LocalDate firstDay = today.withDayOfMonth(1);

		LocalDate lastDay =
		        LocalDate.now();

		Double monthlySales =
		        saleRepository
		                .findBySaleDateBetween(
		                        firstDay,
		                        lastDay)
		                .stream()
		                .mapToDouble(Sale::getTotalAmount)
		                .sum();
		
		// Customer Pending
		Double customerPending =
		        saleRepository
		                .findByRemainingAmountGreaterThan(0.0)
		                .stream()
		                .mapToDouble(Sale::getRemainingAmount)
		                .sum();
		
		// Supplier Pending
		Double supplierPending =
		        purchaseRepository
		                .findByRemainingAmountGreaterThan(0.0)
		                .stream()
		                .mapToDouble(Purchase::getRemainingAmount)
		                .sum();
		
		Double totalProfit = saleRepository.findAll()
		        .stream()
		        .flatMap(sale -> sale.getSaleItems().stream())
		        .filter(item -> item.getProduct() != null)
		        .mapToDouble(item ->
		                (item.getProduct().getSellingPrice()
		                 - item.getProduct().getPurchasePrice())
		                 * item.getQuantity())
		        .sum();
		
		return new DashboardResponseDto(
		        todaySales,
		        monthlySales,
		        customerPending,
		        supplierPending,
		        totalProfit
		);
	}
}
