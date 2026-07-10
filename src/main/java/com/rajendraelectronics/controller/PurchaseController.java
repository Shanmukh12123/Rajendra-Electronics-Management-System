package com.rajendraelectronics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajendraelectronics.dto.PurchaseRequestDto;
import com.rajendraelectronics.dto.PurchaseResponseDto;
import com.rajendraelectronics.service.PurchaseService;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
	
	private final PurchaseService purchaseService;

	public PurchaseController(PurchaseService purchaseService) {
		super();
		this.purchaseService = purchaseService;
	}
	
	@PostMapping("")
	public PurchaseResponseDto addPurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {
		return purchaseService.addPurchase(purchaseRequestDto);
	}
	
	@GetMapping("/{purchaseId}")
	public PurchaseResponseDto getPurchaseById(@PathVariable Long purchaseId) {
		return purchaseService.getPurchaseById(purchaseId);
	}
	
	@GetMapping("")
	public List<PurchaseResponseDto> getAllPurchases() {
		return purchaseService.getAllPurchases();
	}
	
	@DeleteMapping("/{purchaseId}")
	public String deletePurchaseById(@PathVariable Long purchaseId) {
		return purchaseService.deletePurchaseById(purchaseId);
	}
}
