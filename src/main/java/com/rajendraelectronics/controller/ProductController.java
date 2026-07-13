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

import com.rajendraelectronics.dto.LowStockProductDto;
import com.rajendraelectronics.dto.ProductRequestDto;
import com.rajendraelectronics.dto.ProductResponseDto;
import com.rajendraelectronics.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto);
    }

    @GetMapping("")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/{productId}")
	public ProductResponseDto getProductById(@PathVariable Long productId) {
    	return productService.getProductById(productId);
    }
    
    @PutMapping("{productId}")
    public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto dto) { 
    	return productService.updateProduct(productId, dto);
    }
    
    @DeleteMapping("{productId}")
	public String deleteProduct(@PathVariable Long productId) {
		return productService.deleteProduct(productId);
	}
    
    @GetMapping("/low-stock")
    public List<LowStockProductDto> getLowStockProducts() {
        return productService.getLowStockProducts();
    }

}
