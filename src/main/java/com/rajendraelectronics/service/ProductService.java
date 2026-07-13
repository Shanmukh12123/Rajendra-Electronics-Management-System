package com.rajendraelectronics.service;

import java.util.List;

import com.rajendraelectronics.dto.LowStockProductDto;
import com.rajendraelectronics.dto.ProductRequestDto;
import com.rajendraelectronics.dto.ProductResponseDto;

public interface ProductService {
	
	ProductResponseDto addProduct(ProductRequestDto productRequestDto);
	ProductResponseDto getProductById(Long productId);
	ProductResponseDto updateProduct(Long productId, ProductRequestDto dto);
	String deleteProduct(Long productId);
	List<ProductResponseDto> getAllProducts();
	
	List<LowStockProductDto> getLowStockProducts();
	
	
}