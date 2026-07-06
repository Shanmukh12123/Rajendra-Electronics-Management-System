package com.rajendraelectronics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rajendraelectronics.entity.Product;
import com.rajendraelectronics.service.ProductService;

@RestController
public class ProductController {

	ProductService productService;
	
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@PostMapping("/create")
	public String createProduct(@RequestBody Product p) {
		productService.createProduct(p);
		return "Product is created";
	}
	
	@GetMapping("/get/{id}")
	public Product getProduct(@PathVariable Long id) {
        Product p = productService.getProduct(id);
        return p;
	}
}
