package com.rajendraelectronics.dto;

public record CustomerResponseDto(
	Long id,
	String customerName,
	String mobileNumber,
	String email,
	String address
) {}
