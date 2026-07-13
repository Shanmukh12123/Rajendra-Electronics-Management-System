package com.rajendraelectronics.dto;

public record DashboardResponseDto(
		Double todaySales,
        Double monthlySales,
        Double customerPending,
        Double supplierPending,
        Double totalProfit) {

}
