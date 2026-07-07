package com.rajendraelectronics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajendraelectronics.dto.CustomerRequestDto;
import com.rajendraelectronics.dto.CustomerResponseDto;
import com.rajendraelectronics.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@PostMapping("")
	public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
		return customerService.addCustomer(customerRequestDto);
	}
	
	@GetMapping("")
	public List<CustomerResponseDto> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/{customerId}")
	public CustomerResponseDto getCustomerById(@PathVariable Long customerId) {
		return customerService.getCustomerById(customerId);
	}
	
	@PutMapping("/{customerId}")
	public CustomerResponseDto updateCustomer(@PathVariable Long customerId, 
			@RequestBody CustomerRequestDto dto) {
		return customerService.updateCustomer(customerId, dto);
	}
	
	@DeleteMapping("/{customerId}")
	public String deleteCustomer(@PathVariable Long customerId) {
		return customerService.deleteCustomer(customerId);
	}
}
