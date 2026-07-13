package com.rajendraelectronics.dto;

public record LowStockProductDto(
		 Long productId,
	     String productName,
	     String brand,
	     Integer quantityInStock,
	     Integer reorderThreshold) {

}
