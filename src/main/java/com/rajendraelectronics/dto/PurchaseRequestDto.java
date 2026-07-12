package com.rajendraelectronics.dto;

public record PurchaseRequestDto(
		
		Long supplierId,
		Long productId,
		Integer quantity,
		Double purchasePrice,
		Double paidAmount
	)
{}
