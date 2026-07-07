package com.rajendraelectronics.dto;

public record SupplierRequestDto(
        String supplierName,
        String mobileNumber,
        String email,
        String address
) {
}
