package com.rajendraelectronics.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.dto.LowStockProductDto;
import com.rajendraelectronics.dto.ProductRequestDto;
import com.rajendraelectronics.dto.ProductResponseDto;
import com.rajendraelectronics.entity.Product;
import com.rajendraelectronics.repository.ProductRepository;


@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {

        Product product = new Product();

        product.setName(productRequestDto.name());
        product.setBrand(productRequestDto.brand());
        product.setCategory(productRequestDto.category());
        product.setPurchasePrice(productRequestDto.purchasePrice());
        product.setSellingPrice(productRequestDto.sellingPrice());
        product.setQuantityInStock(productRequestDto.quantityInStock());
        product.setReorderThreshold(productRequestDto.reorderThreshold());

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getBrand(),
                savedProduct.getCategory(),
                savedProduct.getPurchasePrice(),
                savedProduct.getSellingPrice(),
                savedProduct.getQuantityInStock(),
                savedProduct.getReorderThreshold()
        );
    }

	@Override
	public ProductResponseDto getProductById(Long productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product != null) {
			return new ProductResponseDto(product.getId(), product.getName(), product.getBrand(), product.getCategory(),
					product.getPurchasePrice(), product.getSellingPrice(), product.getQuantityInStock(),
					product.getReorderThreshold());
		}
		
		throw new RuntimeException("Product not found with id: " + productId);
	}

	@Override
	public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product != null) {
			product.setName(dto.name());
			product.setBrand(dto.brand());
			product.setCategory(dto.category());
			product.setPurchasePrice(dto.purchasePrice());
			product.setSellingPrice(dto.sellingPrice());
			product.setQuantityInStock(dto.quantityInStock());
			product.setReorderThreshold(dto.reorderThreshold());

			Product updatedProduct = productRepository.save(product);

			return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getBrand(),
					updatedProduct.getCategory(), updatedProduct.getPurchasePrice(), updatedProduct.getSellingPrice(),
					updatedProduct.getQuantityInStock(), updatedProduct.getReorderThreshold());
		}
		throw new RuntimeException("Product not found with id: " + productId);
	}

	@Override
	public String deleteProduct(Long productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product != null) {
			productRepository.delete(product);
			return "Product Deleted Successfully";
		}
		return "Product Not Found";
	}

	@Override
	public List<ProductResponseDto> getAllProducts() {
		return productRepository.findAll()
				.stream()
				.map(product -> new ProductResponseDto(
						product.getId(),
						product.getName(),
						product.getBrand(),
						product.getCategory(),
						product.getPurchasePrice(),
						product.getSellingPrice(),
						product.getQuantityInStock(),
						product.getReorderThreshold()
				))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<LowStockProductDto> getLowStockProducts() {

	    return productRepository.findAll()
	            .stream()
	            .filter(product ->
	                    product.getQuantityInStock()
	                    <= product.getReorderThreshold())
	            .map(product -> new LowStockProductDto(
	                    product.getId(),
	                    product.getName(),
	                    product.getBrand(),
	                    product.getQuantityInStock(),
	                    product.getReorderThreshold()))
	            .toList();
	}
	
}