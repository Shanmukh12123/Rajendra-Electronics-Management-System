package com.rajendraelectronics.dto;

public record CustomerRequestDto(
	String customerName,
	String mobileNumber,
	String email,
	String address
) {}
