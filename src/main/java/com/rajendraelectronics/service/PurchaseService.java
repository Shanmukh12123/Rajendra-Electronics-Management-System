package com.rajendraelectronics.service;

import java.util.List;

import com.rajendraelectronics.dto.PurchaseRequestDto;
import com.rajendraelectronics.dto.PurchaseResponseDto;

public interface PurchaseService {
	
	PurchaseResponseDto addPurchase(PurchaseRequestDto dto);
	
	PurchaseResponseDto getPurchaseById(Long purchaseId);
	
	List<PurchaseResponseDto> getAllPurchases();
	
	String deletePurchaseById(Long purchaseId);
	
}
