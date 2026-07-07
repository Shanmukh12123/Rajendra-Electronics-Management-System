package com.rajendraelectronics.service;

import java.util.List;

import com.rajendraelectronics.dto.CustomerRequestDto;
import com.rajendraelectronics.dto.CustomerResponseDto;

public interface CustomerService {
	
	CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto);
	
	CustomerResponseDto getCustomerById(Long customerId);
	
	CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto dto);
	
	String deleteCustomer(Long customerId);
	
	List<CustomerResponseDto> getAllCustomers();
}
