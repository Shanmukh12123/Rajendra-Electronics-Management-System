package com.rajendraelectronics.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.dto.RecentSaleDto;
import com.rajendraelectronics.dto.SaleItemRequestDto;
import com.rajendraelectronics.dto.SalePaymentDto;
import com.rajendraelectronics.dto.SaleRequestDto;
import com.rajendraelectronics.dto.SaleResponseDto;
import com.rajendraelectronics.entity.Customer;
import com.rajendraelectronics.entity.Product;
import com.rajendraelectronics.entity.Sale;
import com.rajendraelectronics.entity.SaleItem;
import com.rajendraelectronics.repository.CustomerRepository;
import com.rajendraelectronics.repository.ProductRepository;
import com.rajendraelectronics.repository.SaleItemRepository;
import com.rajendraelectronics.repository.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleServiceImpl implements SaleService {
	
	private final SaleRepository saleRepository;
	private final SaleItemRepository saleItemRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	
	public SaleServiceImpl(SaleRepository saleRepository, SaleItemRepository saleItemRepository,
			CustomerRepository customerRepository, ProductRepository productRepository) {
		super();
		this.saleRepository = saleRepository;
		this.saleItemRepository = saleItemRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
	}

	@Transactional
	@Override
	public SaleResponseDto createSale(SaleRequestDto dto) {
		
		Customer customer = customerRepository.findById(dto.customerId())  // Find customer
				.orElseThrow(() -> 
				       new RuntimeException("Customer not found"));
		
		Sale sale = new Sale();

		sale.setCustomer(customer);
		sale.setSaleDate(LocalDate.now());
		sale.setPaymentType(dto.paymentType());
		sale.setInvoiceNumber("INV-" + System.currentTimeMillis());
		
		Double totalAmount = 0.0;

		List<SaleItem> saleItems = new ArrayList<>();
		for (SaleItemRequestDto itemDto : dto.items()) {
			SaleItem saleItem = new SaleItem();

			saleItem.setSale(sale);
			saleItem.setQuantity(itemDto.quantity());
			saleItem.setUnitPrice(itemDto.unitPrice());
			
			// Product Logic
			if (itemDto.productId() != null) {

			    Product product = productRepository.findById(itemDto.productId())
			            .orElseThrow(() ->
			                    new RuntimeException("Product not found"));

			    if (product.getQuantityInStock() < itemDto.quantity()) {
			        throw new RuntimeException("Insufficient stock available");
			    }

			    product.setQuantityInStock(
			            product.getQuantityInStock() - itemDto.quantity());

			    productRepository.save(product);

			    saleItem.setProduct(product);
			    saleItem.setProductName(product.getName());
			}
			else {
			    saleItem.setProduct(null);
			    saleItem.setProductName(itemDto.productName());
			}
			
			// Calculate Item Total
			Double itemTotal =
			        itemDto.quantity() * itemDto.unitPrice();
			saleItem.setTotalPrice(itemTotal);
			totalAmount += itemTotal;
			saleItems.add(saleItem);
		}
		
		// Calculate Payment
		Double paidAmount =
		        dto.paidAmount() != null
		        ? dto.paidAmount()
		        : 0.0;
		if (paidAmount > totalAmount) {                  // Validation
		    throw new RuntimeException(
		            "Paid amount cannot be greater than total amount");
		}
		
		sale.setTotalAmount(totalAmount);
		sale.setPaidAmount(paidAmount);
		sale.setRemainingAmount(totalAmount - paidAmount);

		sale.setSaleItems(saleItems);
		Sale savedSale = saleRepository.save(sale);
		
		return new SaleResponseDto(
		        savedSale.getId(),
		        savedSale.getInvoiceNumber(),
		        customer.getCustomerName(),
		        savedSale.getTotalAmount(),
		        savedSale.getPaidAmount(),
		        savedSale.getRemainingAmount(),
		        savedSale.getPaymentType(),
		        savedSale.getSaleDate()
		);
		
	}

	@Transactional
	@Override
	public SaleResponseDto updateSalePayment(Long saleId, SalePaymentDto dto) {
		Sale sale = saleRepository.findById(saleId)
		        .orElseThrow(() ->
		                new RuntimeException("Sale not found"));

		Double paymentAmount = dto.amount();
		
		// Validation 1: Null or Negative Amount
		if (paymentAmount == null || paymentAmount <= 0) {
		    throw new RuntimeException(
		            "Payment amount must be greater than zero");
		}
		
		// Payment Greater Than Remaining Amount
		if (paymentAmount > sale.getRemainingAmount()) {
		    throw new RuntimeException(
		            "Payment amount cannot be greater than remaining amount");
		}
		
		// Update Amount
		sale.setPaidAmount(
		        sale.getPaidAmount() + paymentAmount);

		sale.setRemainingAmount(
		        sale.getRemainingAmount() - paymentAmount);
		
		// Save
		Sale updatedSale = saleRepository.save(sale);
		
		return new SaleResponseDto(
		        updatedSale.getId(),
		        updatedSale.getInvoiceNumber(),
		        updatedSale.getCustomer().getCustomerName(),
		        updatedSale.getTotalAmount(),
		        updatedSale.getPaidAmount(),
		        updatedSale.getRemainingAmount(),
		        updatedSale.getPaymentType(),
		        updatedSale.getSaleDate()
		);
	}
	
	@Override
	public List<SaleResponseDto> getPendingSales() {

	    return saleRepository
	            .findByRemainingAmountGreaterThan(0.0)
	            .stream()
	            .map(this::toResponseDto)
	            .toList();
	}
	
	@Override
	public List<RecentSaleDto> getRecentSales() {

	    return saleRepository
	            .findTop5ByOrderBySaleDateDesc()
	            .stream()
	            .map(sale -> new RecentSaleDto(
	                    sale.getInvoiceNumber(),
	                    sale.getCustomer().getCustomerName(),
	                    sale.getTotalAmount(),
	                    sale.getSaleDate()
	            ))
	            .toList();
	}
	
	private SaleResponseDto toResponseDto(Sale sale) {

	    return new SaleResponseDto(
	            sale.getId(),
	            sale.getInvoiceNumber(),
	            sale.getCustomer().getCustomerName(),
	            sale.getTotalAmount(),
	            sale.getPaidAmount(),
	            sale.getRemainingAmount(),
	            sale.getPaymentType(),
	            sale.getSaleDate()
	    );
	}
	
}
