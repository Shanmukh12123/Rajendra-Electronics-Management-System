package com.rajendraelectronics.dto;

import java.time.LocalDate;

public record RecentSaleDto(
		String invoiceNumber,
        String customerName,
        Double totalAmount,
        LocalDate saleDate) {

}
