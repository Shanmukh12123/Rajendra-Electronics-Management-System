package com.rajendraelectronics.dto;

public record RegisterRequest(
		String username,
		String email,
		String password,
		String mobile
		) {}
