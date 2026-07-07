package com.rajendraelectronics.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.dto.CustomerRequestDto;
import com.rajendraelectronics.dto.CustomerResponseDto;
import com.rajendraelectronics.entity.Customer;
import com.rajendraelectronics.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = toEntity(customerRequestDto);
        Customer saved = customerRepository.save(customer);
        return toResponseDto(saved);
    }

    @Override
    public CustomerResponseDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        return toResponseDto(customer);
    }

    @Override
    public CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto dto) {
        Customer existing = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        existing.setCustomerName(dto.customerName());
        existing.setMobileNumber(dto.mobileNumber());
        existing.setEmail(dto.email());
        existing.setAddress(dto.address());

        Customer updated = customerRepository.save(existing);
        return toResponseDto(updated);
    }

    @Override
    public String deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
        customerRepository.deleteById(customerId);
        return "Customer deleted successfully";
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private Customer toEntity(CustomerRequestDto dto) {
        Customer c = new Customer();
        c.setCustomerName(dto.customerName());
        c.setMobileNumber(dto.mobileNumber());
        c.setEmail(dto.email());
        c.setAddress(dto.address());
        return c;
    }

    private CustomerResponseDto toResponseDto(Customer c) {
        return new CustomerResponseDto(c.getId(), c.getCustomerName(), c.getMobileNumber(), c.getEmail(), c.getAddress());
    }
}
