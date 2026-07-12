package com.rajendraelectronics.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.dto.PurchasePaymentDto;
import com.rajendraelectronics.dto.PurchaseRequestDto;
import com.rajendraelectronics.dto.PurchaseResponseDto;
import com.rajendraelectronics.entity.Product;
import com.rajendraelectronics.entity.Purchase;
import com.rajendraelectronics.entity.Supplier;
import com.rajendraelectronics.repository.ProductRepository;
import com.rajendraelectronics.repository.PurchaseRepository;
import com.rajendraelectronics.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final SupplierRepository supplierRepository;
	private final ProductRepository productRepository;

	public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
							   SupplierRepository supplierRepository,
							   ProductRepository productRepository) {
		this.purchaseRepository = purchaseRepository;
		this.supplierRepository = supplierRepository;
		this.productRepository = productRepository;
	}
	
	@Transactional
	@Override
	public PurchaseResponseDto addPurchase(PurchaseRequestDto dto) {

	    Supplier supplier = supplierRepository.findById(dto.supplierId())
	            .orElseThrow(() -> new RuntimeException(
	                    "Supplier not found with id: " + dto.supplierId()));

	    Product product = productRepository.findById(dto.productId())
	            .orElseThrow(() -> new RuntimeException(
	                    "Product not found with id: " + dto.productId()));

	    Purchase purchase = new Purchase();

	    purchase.setSupplier(supplier);
	    purchase.setProduct(product);
	    purchase.setQuantity(dto.quantity());
	    purchase.setPurchasePrice(dto.purchasePrice());

	    // Calculate Total Amount
	    double totalAmount = dto.purchasePrice()
	            * (dto.quantity() != null ? dto.quantity() : 0);

	    purchase.setTotalAmount(totalAmount);

	    // Payment Calculation
	   
	    double paidAmount = dto.paidAmount() != null
	            ? dto.paidAmount()
	            : 0.0;

	    // Validation
	    if (paidAmount > totalAmount) {
	        throw new RuntimeException(
	                "Paid amount cannot be greater than total amount");
	    }

	    double remainingAmount = totalAmount - paidAmount;

	    purchase.setPaidAmount(paidAmount);
	    purchase.setRemainingAmount(remainingAmount);

	    purchase.setPurchaseDate(LocalDate.now());

	    // Inventory Update
	    Integer currentStock = product.getQuantityInStock();

	    if (currentStock == null) {
	        currentStock = 0;
	    }

	    Integer addQty = dto.quantity() != null
	            ? dto.quantity()
	            : 0;

	    product.setQuantityInStock(currentStock + addQty);

	    productRepository.save(product);

	    Purchase savedPurchase = purchaseRepository.save(purchase);

	    return toResponseDto(savedPurchase);
	}

	@Override
	public PurchaseResponseDto getPurchaseById(Long purchaseId) {
		Purchase p = purchaseRepository.findById(purchaseId)
				.orElseThrow(() -> new RuntimeException("Purchase not found with id: " + purchaseId));
		return toResponseDto(p);
	}

	@Override
	public List<PurchaseResponseDto> getAllPurchases() {
		return purchaseRepository.findAll()
				.stream()
				.map(this::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public String deletePurchaseById(Long purchaseId) {
		if (!purchaseRepository.existsById(purchaseId)) {
			throw new RuntimeException("Purchase not found with id: " + purchaseId);
		}
		purchaseRepository.deleteById(purchaseId);
		return "Purchase deleted successfully";
	}

	private PurchaseResponseDto toResponseDto(Purchase p) {

	    String supplierName =
	            p.getSupplier() != null
	                    ? p.getSupplier().getSupplierName()
	                    : null;

	    String productName =
	            p.getProduct() != null
	                    ? p.getProduct().getName()
	                    : null;

	    return new PurchaseResponseDto(
	            p.getId(),
	            supplierName,
	            productName,
	            p.getQuantity(),
	            p.getPurchasePrice(),
	            p.getTotalAmount(),
	            p.getPaidAmount(),
	            p.getRemainingAmount(),
	            p.getPurchaseDate()
	    );
	}
	
	@Override
	@Transactional
	public PurchaseResponseDto updatePurchasePayment(
	        Long purchaseId,
	        PurchasePaymentDto dto) {

	    Purchase purchase = purchaseRepository.findById(purchaseId)
	            .orElseThrow(() -> new RuntimeException(
	                    "Purchase not found with id: " + purchaseId));

	    Double paymentAmount = dto.amount();

	    if (paymentAmount == null || paymentAmount <= 0) {
	        throw new RuntimeException(
	                "Payment amount must be greater than zero");
	    }

	    if (paymentAmount > purchase.getRemainingAmount()) {
	        throw new RuntimeException(
	                "Payment amount cannot be greater than remaining amount");
	    }

	    purchase.setPaidAmount(
	            purchase.getPaidAmount() + paymentAmount);

	    purchase.setRemainingAmount(
	            purchase.getRemainingAmount() - paymentAmount);

	    Purchase updatedPurchase =
	            purchaseRepository.save(purchase);

	    return toResponseDto(updatedPurchase);
	}

	@Override
	public List<PurchaseResponseDto> getPendingPurchases() {
		
		 return purchaseRepository
		            .findByRemainingAmountGreaterThan(0.0)
		            .stream()
		            .map(this::toResponseDto)
		            .collect(Collectors.toList());
	}


}
