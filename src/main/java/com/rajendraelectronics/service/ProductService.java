package com.rajendraelectronics.service;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.entity.Product;
import com.rajendraelectronics.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void createProduct(Product p) {
		productRepository.save(p);
	}

	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}
}
