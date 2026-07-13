package com.rajendraelectronics.dto;

import java.time.LocalDate;

public record SaleResponseDto(
		Long saleId,
		
		String invoiceNumber,

        String customerName,

        Double totalAmount,

        Double paidAmount,

        Double remainingAmount,

        String paymentType,

        LocalDate saleDate
        ) {}
