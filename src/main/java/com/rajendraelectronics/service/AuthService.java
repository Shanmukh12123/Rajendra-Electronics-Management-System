package com.rajendraelectronics.service;

import com.rajendraelectronics.dto.AuthResponse;
import com.rajendraelectronics.dto.LoginRequest;
import com.rajendraelectronics.dto.RegisterRequest;

public interface AuthService {

	String register(RegisterRequest request);
	
	AuthResponse login(LoginRequest request);
}
