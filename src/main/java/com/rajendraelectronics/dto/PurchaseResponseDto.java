package com.rajendraelectronics.dto;

import java.time.LocalDate;

public record PurchaseResponseDto(
		
		Long id,
		String supplierName,
		String productName,
		Integer quantity,
		Double purchasePrice,
		Double totalAmount,
		LocalDate purchaseDate
	)
{}
