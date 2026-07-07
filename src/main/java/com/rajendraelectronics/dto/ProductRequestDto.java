package com.rajendraelectronics.dto;

public record ProductRequestDto(
		String name,
		String brand,
        String category,
        Double purchasePrice,
        Double sellingPrice,
        Integer quantityInStock,
        Integer reorderThreshold
        ) {}
