package com.rajendraelectronics.dto;

public record SupplierResponseDto(
        Long id,
        String supplierName,
        String mobileNumber,
        String email,
        String address
) {
}
