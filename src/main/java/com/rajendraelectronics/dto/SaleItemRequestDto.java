package com.rajendraelectronics.dto;

public record SaleItemRequestDto(
		Long productId,        // optional
        String productName,    // for manual item

        Integer quantity,

        Double unitPrice
        ) {}
