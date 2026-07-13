package com.rajendraelectronics.dto;

import java.util.List;

public record SaleRequestDto(
		Long customerId,

        List<SaleItemRequestDto> items,

        Double paidAmount,

        String paymentType
        ) {}
