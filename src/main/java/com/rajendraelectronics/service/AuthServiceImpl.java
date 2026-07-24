package com.rajendraelectronics.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rajendraelectronics.config.PasswordConfig;
import com.rajendraelectronics.dto.AuthResponse;
import com.rajendraelectronics.dto.LoginRequest;
import com.rajendraelectronics.dto.RegisterRequest;
import com.rajendraelectronics.entity.User;
import com.rajendraelectronics.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String register(RegisterRequest request) {
		
		if (userRepository.existsByUsername(request.username())) {
			throw new RuntimeException("Username already exists");
		}
		
		if (userRepository.existsByEmail(request.email())) {
			throw new RuntimeException("Email already exists");
		}
		
		if (userRepository.existsByMobile(request.mobile())) {
			throw new RuntimeException("Mobile number already exists");
		}
		
		User user = new User(
				request.username(),
				request.email(),
				passwordEncoder.encode(request.password()),
				request.mobile()
		);
		
		userRepository.save(user);
		return "User Register Successfully";
		
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
