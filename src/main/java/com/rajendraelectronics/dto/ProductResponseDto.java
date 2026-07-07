package com.rajendraelectronics.dto;

public record ProductResponseDto(
		Long id,
        String name,
        String brand,
        String category,
        Double purchasePrice,
        Double sellingPrice,
        Integer quantityInStock,
        Integer reorderThreshold
        ) {}
